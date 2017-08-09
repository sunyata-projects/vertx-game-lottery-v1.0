package com.xt.landlords.game.eliminate;

import com.xt.landlords.GameManager;
import com.xt.landlords.GameTypes;
import com.xt.landlords.account.Account;
import com.xt.landlords.exception.BetErrorException;
import com.xt.landlords.exception.ExchangeErrorException;
import com.xt.landlords.game.eliminate.condition.BetCondition;
import com.xt.landlords.game.eliminate.condition.DealCondition;
import com.xt.landlords.game.eliminate.phase.EliminateClearPhaseData;
import com.xt.landlords.game.eliminate.phase.EliminatePlayPhaseData;
import com.xt.landlords.game.eliminate.phase.EliminatePlayPhaseDataItem;
import com.xt.landlords.game.phase.ExchangePhaseData;
import com.xt.landlords.ioc.SpringIocUtil;
import com.xt.landlords.service.*;
import com.xt.landlords.statemachine.GameController;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.squirrelframework.foundation.fsm.HistoryType;
import org.squirrelframework.foundation.fsm.StateMachineStatus;
import org.squirrelframework.foundation.fsm.annotation.*;
import org.sunyata.octopus.model.GameModel;

import java.math.BigDecimal;

/**
 * Created by leo on 17/4/26.
 */

@States({
        @State(name = "Init", initialState = true),
        @State(name = "Exchange"),

        @State(name = "Play", historyType = HistoryType.DEEP),
        @State(parent = "Play", name = "Bet", initialState = true),
        @State(parent = "Play", name = "Deal"),

        @State(name = "GameOver"),
})
@Transitions({
        @Transit(from = "Init", to = "Exchange", on = "Exchange", callMethod = "OnExchange"),
        @Transit(from = "Exchange", to = "Bet", on = "Bet", callMethod = "OnBet"),

        @Transit(from = "Bet", to = "Deal", on = "Deal", callMethod = "OnDeal", when = DealCondition.class),
        @Transit(from = "Deal", to = "Bet", on = "Bet", callMethod = "OnBet", when = BetCondition.class),
        @Transit(from = "Exchange", to = "GameOver", on = "GameOver", callMethod = "OnGameOver"),
        @Transit(from = "Deal", to = "GameOver", on = "GameOver", callMethod = "OnGameOver"),
        @Transit(from = "Bet", to = "GameOver", on = "GameOver", callMethod = "OnForceGameOver"),
//        @Transit(from = "Deal", to = "GameOver", on = "ForceGameOver", callMethod = "OnForceGameOver",)
})

//@ContextEvent(finishEvent = "OnLuckDraw")
@StateMachineParameters(stateType = GameEliminateState.class, eventType = GameEliminateEvent.class, contextType =
        GameModel.class)
//@Configurable(preConstruction = true)
public class GameEliminateController extends GameController<GameEliminateModel, GameEliminateController,
        GameEliminateState,
        GameEliminateEvent,
        GameModel> {
    //    @Autowired
//    private ApplicationContext applicationContext;
    Logger logger = LoggerFactory.getLogger(GameEliminateController.class);
    private Logger LOGGER = LoggerFactory.getLogger(GameEliminateController.class);

    public void OnExchange(GameEliminateState from, GameEliminateState to, GameEliminateEvent event,
                           GameModel context) throws Exception {
        //兑换点数
        ExchangePhaseData phaseData = (ExchangePhaseData) getPhaseData(GameEliminateState.Exchange
                .getValue());
        GameEliminateModel gameModel = getGameModel();
        ExchangeService exchangeService = SpringIocUtil.getBean(ExchangeService.class);
        ExchangeResult exchange = exchangeService.exchange(gameModel.getUserName(), phaseData.getAmt(), String
                .valueOf(GameTypes
                        .Eliminate.getValue()));
        if (!StringUtils.isEmpty(exchange.getErrorMessage())) {
            throw new ExchangeErrorException("预支点数失败,请重试");
        }
        phaseData.setGamePoint(exchange.getGamePoint());
        phaseData.setSerialNo(exchange.getSerialNo());
        setPhaseSuccess(GameEliminateState.Exchange.getValue());

        logger.info("on exchange");
    }

    public void OnBet(GameEliminateState from, GameEliminateState to, GameEliminateEvent event,
                      GameModel context) throws Exception {

        EliminatePlayPhaseData phaseData = (EliminatePlayPhaseData) getPhaseData(GameEliminateState.Play.getValue());
        GameEliminateModel gameModel = getGameModel();
        EliminatePlayPhaseDataItem lastPlayPhaseDataItem = phaseData.getLastPlayPhaseDataItem();
        if (lastPlayPhaseDataItem.isOver()) {
            throw new Exception("游戏状态问题");
        }

        EliminatePlayPhaseDataItem penultimateDataItem = phaseData.getPenultimateDataItem();//倒数第二次下注发牌数量
        int exchangeGamePointBalance = 0;//剩余兑换点点数
        int awardGamePoint = 0;//剩作奖励点数
        int totalDoubleKingCount = 0;
        if (penultimateDataItem == null) {//是第一次下注
            ExchangePhaseData exchangePhaseData = (ExchangePhaseData) getPhaseData(GameEliminateState.Exchange
                    .getValue());
            exchangeGamePointBalance = exchangePhaseData.getGamePoint();
            awardGamePoint = exchangePhaseData.getSavedPoint();

        } else {
            exchangeGamePointBalance = penultimateDataItem.getExchangeGamePointBalance();
            awardGamePoint = penultimateDataItem.getTotalAwardGamePoint();
            totalDoubleKingCount = penultimateDataItem.getTotalDoubleKingCount();
        }
        //本次下注消耗
        int betGamePoint = lastPlayPhaseDataItem.getBetGamePoint();
        //剩余兑换点数
        exchangeGamePointBalance = (awardGamePoint >= betGamePoint) ? exchangeGamePointBalance :
                (exchangeGamePointBalance - (betGamePoint - awardGamePoint));
        logger.info("剩余兑换点数为:{}", exchangeGamePointBalance);
        //剩余奖励点数
        awardGamePoint = awardGamePoint >= betGamePoint ? (awardGamePoint - betGamePoint) : 0;

        GamePointBetService gamePointBetService = SpringIocUtil.getBean(GamePointBetService.class);
        GamePointBetResult gamePointBetResult = gamePointBetService.bet(gameModel.getUserName(),
                lastPlayPhaseDataItem.getBetGamePoint(), gameModel.getGameInstanceId());
        if (!StringUtils.isEmpty(gamePointBetResult.getErrorMessage())) {
            logger.error(gamePointBetResult.getErrorMessage());
            throw new BetErrorException("下注失败,请重试");
        }


        awardGamePoint += gamePointBetResult.getAwardGamePoint();//累加本次奖励点数
        totalDoubleKingCount += gamePointBetResult.getDoubleKingCount();//累加本次双王数量
        lastPlayPhaseDataItem
                .setSerialNo(gamePointBetResult.getSerialNo())
                .setExchangeGamePointBalance(exchangeGamePointBalance)
                .setAwardGamePoint(gamePointBetResult.getAwardGamePoint())
                .setDoubleKingCount(gamePointBetResult.getDoubleKingCount())
                .setTotalAwardGamePoint(awardGamePoint)
                .setAwardLevel(gamePointBetResult.getAwardLevel())
                .setTotalDoubleKingCount(totalDoubleKingCount);
//                .setCards(gamePointBetResult.getCards());
        logger.info("on play");
    }

    public void OnDeal(GameEliminateState from, GameEliminateState to, GameEliminateEvent event,
                       GameModel context) throws Exception {
        //
        EliminatePlayPhaseData phaseData = (EliminatePlayPhaseData) getPhaseData(GameEliminateState.Play.getValue());
        EliminatePlayPhaseDataItem lastPlayPhaseDataItem = phaseData.getLastPlayPhaseDataItem();
        if (lastPlayPhaseDataItem.isOver()) {
            throw new Exception("游戏状态问题");
        }
        lastPlayPhaseDataItem.setOver(true);
        //如果达到7个宝箱
        if (lastPlayPhaseDataItem.getTotalDoubleKingCount() == 7) {
            setPhaseSuccess(GameEliminateState.Play.getValue());
        }
        logger.info("on Deal");
    }

    public void OnGameOver(GameEliminateState from, GameEliminateState to, GameEliminateEvent event,
                           GameModel context) throws Exception {
        BigDecimal totalMoney = BigDecimal.ZERO;
        EliminateClearPhaseData phaseData = (EliminateClearPhaseData) getPhaseData(GameEliminateState.GameOver.getValue
                ());
        GameEliminateModel gameModel = getGameModel();
        EliminateLastBetService lastBetService = SpringIocUtil.getBean(EliminateLastBetService.class);


        EliminatePlayPhaseData playPhaseData = (EliminatePlayPhaseData) getPhaseData(GameEliminateState.Play
                .getValue());
        EliminatePlayPhaseDataItem lastPlayPhaseDataItem = null;
        if (playPhaseData != null) {
            lastPlayPhaseDataItem = playPhaseData.getLastPlayPhaseDataItem();

            EliminateLastBetResult lastBetResult = lastBetService.bet(gameModel.getUserName(), lastPlayPhaseDataItem
                    .getBetGamePoint(), "", lastPlayPhaseDataItem.getAwardLevel() == 99);

            //lastBetResult.setTotalMoney((int) (lastPlayPhaseDataItem.getExchangeGamePointBalance() / 100.00));
            if (!StringUtils.isEmpty(lastBetResult.getErrorMessage())) {
                throw new BetErrorException("下注失败,请重试");
            }
            totalMoney = lastBetResult.getTotalMoney();
            phaseData.setSerialNo(lastBetResult.getSerialNo()).setTotalMoney(totalMoney);
        }

        setPhaseSuccess(GameEliminateState.GameOver.getValue());

//        EliminatePlayPhaseData playPhaseData = (EliminatePlayPhaseData) getPhaseData(GameEliminateState.Play.getValue
//                ());
        int exchangeGamePointBalance = 0;
        if (lastPlayPhaseDataItem == null) {
            ExchangePhaseData exchangePhaseData = (ExchangePhaseData) getPhaseData(GameEliminateState.Exchange
                    .getValue());
            exchangeGamePointBalance = exchangePhaseData.getGamePoint();
        } else {
            //lastPlayPhaseDataItem = playPhaseData.getLastPlayPhaseDataItem();
            exchangeGamePointBalance = lastPlayPhaseDataItem.getExchangeGamePointBalance();
        }
        BigDecimal money = new BigDecimal(exchangeGamePointBalance).divide(new BigDecimal(100.00));
        //float money = (float) (exchangeGamePointBalance * 1.00f / 100.00);
        logger.info("游戏正常结束,归还游戏点数,换算成人民向为:{}", money);
        Account.addBalance(gameModel.getUserName(), money);
        logger.info("游戏正常结束,奖金关获得奖奖励:{}", totalMoney);
        Account.addBalance(gameModel.getUserName(), totalMoney);
        logger.info("game over");
    }

    public void OnForceGameOver(GameEliminateState from, GameEliminateState to, GameEliminateEvent event,
                                GameModel context) throws Exception {
        setPhaseSuccess(GameEliminateState.GameOver.getValue());

        EliminatePlayPhaseData playPhaseData = (EliminatePlayPhaseData) getPhaseData(GameEliminateState.Play.getValue
                ());
        EliminatePlayPhaseDataItem lastPlayPhaseDataItem = playPhaseData.getLastPlayPhaseDataItem();
        GameEliminateModel gameModel = getGameModel();
        ///float money = (float) (lastPlayPhaseDataItem.getExchangeGamePointBalance() * 1.00f / 100.00);
        BigDecimal money = new BigDecimal(lastPlayPhaseDataItem.getExchangeGamePointBalance()).divide(new BigDecimal
                (100.00));

        logger.info("游戏被迫结束,归还游戏点数,换算成人民向为:{}", money);
        Account.addBalance(gameModel.getUserName(), money);
        logger.info("force game over");
    }

    @Override
    protected void afterTransitionCausedException(GameEliminateState fromState, GameEliminateState toState,
                                                  GameEliminateEvent event, GameModel context) {
        //super.afterTransitionCausedException(fromState, toState, event, context);
        if (getLastException().getTargetException() != null) {
            logger.error("状态转换时出错,from:{},to:{},event:{},context:{},errorMsg:{}", fromState, toState, event, context,
                    ExceptionUtils.getStackTrace(getLastException().getTargetException()));
            this.setStatus(StateMachineStatus.IDLE);
        }

        throw getLastException();
    }

    @Override
    protected void beforeActionInvoked(GameEliminateState from, GameEliminateState to, GameEliminateEvent event,
                                       GameModel
                                               context) {
//        addOptionalDot();
    }

    @Override
    protected void afterTransitionCompleted(GameEliminateState fromState, GameEliminateState toState, GameEliminateEvent
            event, GameModel context) throws Exception {

        GameEliminateModel gameModel = getGameModel();
        GameManager bean = SpringIocUtil.getBean(GameManager.class);
        bean.saveGameModelToCacheAndAsyncDb(gameModel);
        if (toState == GameEliminateState.GameOver) {
            GameManager.onGameOver(getGameModel().getUserName());
            bean.clearGameModelFromCache(gameModel.getUserName());
        }
    }

    @Override
    public Integer getGameType() {
        return GameTypes.Eliminate.getValue();
    }

}
