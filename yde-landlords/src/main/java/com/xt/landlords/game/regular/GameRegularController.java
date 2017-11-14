package com.xt.landlords.game.regular;

import com.xt.landlords.GameManager;
import com.xt.landlords.exception.BetErrorException;
import com.xt.landlords.game.phase.BetPhaseData;
import com.xt.landlords.game.phase.BetPhaseModel;
import com.xt.landlords.game.phase.TicketResult;
import com.xt.landlords.game.regular.condition.GuessSizeCondition;
import com.xt.landlords.game.regular.condition.LoseCondition;
import com.xt.landlords.game.regular.condition.PlayingCondition;
import com.xt.landlords.game.regular.condition.WinCondition;
import com.xt.landlords.game.regular.phase.*;
import com.xt.landlords.ioc.SpringIocUtil;
import com.xt.landlords.service.GuessSizeBetService;
import com.xt.landlords.service.MoneyBetService;
import com.xt.landlords.service.RegularLastBetService;
import com.xt.landlords.statemachine.GameController;
import com.xt.yde.GameTypes;
import org.apache.commons.lang.StringUtils;
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
        @State(name = "Bet"),
        @State(name = "Deal"),
        @State(name = "Raise"),
        @State(name = "Dark"),
        @State(name = "Playing"),
        @State(name = "PlayEnd", historyType = HistoryType.DEEP),
        @State(parent = "PlayEnd", name = "Win"),
        @State(parent = "PlayEnd", name = "Lose"),
        @State(name = "GuessSize"),
        @State(name = "LuckDraw"),
        @State(name = "GameOver"),
})
@Transitions({
        @Transit(from = "Init", to = "Bet", on = "Bet", callMethod = "OnBet"),
        @Transit(from = "Bet", to = "Deal", on = "Deal", callMethod = "OnDeal"),
        @Transit(from = "Deal", to = "Raise", on = "Raise", callMethod = "OnRaise"),
        @Transit(from = "Raise", to = "Dark", on = "Dark", callMethod = "OnDark"),
        @Transit(from = "Dark", to = "Playing", on = "Play", callMethod = "OnPlay"),

        @Transit(from = "Playing", to = "Playing", on = "Play", callMethod = "OnPlay", when = PlayingCondition.class),
        @Transit(from = "Playing", to = "Win", on = "Play", callMethod = "OnWin", when = WinCondition.class),
        @Transit(from = "Playing", to = "Lose", on = "Play", callMethod = "OnLose", when = LoseCondition.class),

        @Transit(from = "Win", to = "GuessSize", on = "GuessSize", callMethod = "OnGuessSize", when =
                GuessSizeCondition.class),
        @Transit(from = "Lose", to = "GuessSize", on = "GuessSize", callMethod = "OnGuessSize", when =
                GuessSizeCondition.class),

//        @Transit(from = "Win", to = "LuckDraw", on = "LuckDraw", callMethod = "OnLuckDraw", when = MyCondition.class),
//        @Transit(from = "Lose", to = "LuckDraw", on = "LuckDraw", callMethod = "OnLuckDraw", when = MyCondition
// .class),
        @Transit(from = "GuessSize", to = "GameOver", on = "GameOver", callMethod = "OnGameOver"),
})

//@ContextEvent(finishEvent = "OnLuckDraw")
@StateMachineParameters(stateType = GameRegularState.class, eventType = GameRegularEvent.class, contextType =
        GameModel.class)
//@Configurable(preConstruction = true)
public class GameRegularController extends GameController<GameRegularModel, GameRegularController, GameRegularState,
        GameRegularEvent,
        GameModel> {
    //    @Autowired
//    private ApplicationContext applicationContext;
    private StringBuilder logger = new StringBuilder();
    private Logger LOGGER = LoggerFactory.getLogger(GameRegularController.class);

    public void OnBet(GameRegularState from, GameRegularState to, GameRegularEvent event,
                      GameModel context) throws Exception {
        MoneyBetService moneyBetService = SpringIocUtil.getBean(MoneyBetService.class);

        GameRegularModel gameModel = getGameModel();
        BetPhaseData phaseData = (BetPhaseData) getPhaseData(GameRegularState.Bet.getValue());
        //下注
        TicketResult ticketResult = moneyBetService.betAndQueryPrizeLevel(GameTypes.Regular.getValue(), gameModel
                .getUserName(), phaseData
                .getBetAmt(), gameModel.getGameInstanceId());
        phaseData.setBetSerialNo(ticketResult.getTicketId());
        phaseData.setTicketResult(ticketResult);
        setPhaseSuccess(GameRegularState.Bet.getValue());
        logger.append("on bet");
    }

    public void OnRaise(GameRegularState from, GameRegularState to, GameRegularEvent event,
                        GameModel context) throws Exception {
        MoneyBetService moneyBetService = SpringIocUtil.getBean(MoneyBetService.class);
        GameRegularModel gameModel = getGameModel();
        RaisePhaseModel phase = (RaisePhaseModel) gameModel.getPhase(GameRegularState.Raise.getValue());
        RaisePhaseData phaseData = phase.getPhaseData();
        TicketResult betResult = moneyBetService.betAndQueryPrizeLevel(this.getGameType(), gameModel.getUserName(),
                phaseData.getBetAmt() * (phaseData.getTimes() - 1),
                phase.getGameInstanceId());
        if (StringUtils.isEmpty(betResult.getTicketId())) {
            throw new BetErrorException("下注失败,请重试");
        }
        phaseData.setBetSerialNo(betResult.getTicketId());
        phaseData.setTicketResult(betResult);
        setPhaseSuccess(GameRegularState.Raise.getValue());
        logger.append("on raise");
    }

    //首次发牌
    public void OnDeal(GameRegularState from, GameRegularState to, GameRegularEvent event, GameModel context) throws
            Exception {
//        GamePhaseModel phase = this.getGameModel().getPhase(GameRegularState.Deal.getValue());
//        TicketResult betResult = moneyBetService.betAndQueryPrizeLevel(this.getGameType(), gameModel.getUserName(),
//                phaseData
//                        .getBetAmt(),
//                phase.getGameInstanceId());
//        if (StringUtils.isEmpty(betResult.getTicketId())) {
//            throw new BetErrorException("下注失败,请重试");
//        }
//        phaseData.setBetSerialNo(betResult.getTicketId());
//        phaseData.setTicketResult(betResult);
        setPhaseSuccess(GameRegularState.Bet.getValue());
        logger.append("on raise");
    }


    public void OnPlay(GameRegularState from, GameRegularState to, GameRegularEvent event, GameModel context) throws
            Exception {
        GameRegularModel gameModel = getGameModel();
        RegularPlayPhaseModel phase = (RegularPlayPhaseModel) gameModel.getPhase(GameRegularState.Playing.getValue());
        RegularPlayPhaseDataItem lastDataItem = phase.getPhaseData().getLastDataItem();
//        boolean isAuto = lastDataItem.isAuto();
//        List<Integer> playCards = new ArrayList<>(lastDataItem.getShowCards());
//        int placeRole = lastDataItem.getNowPlace(); //玩家角色 1地主 2右边农民 3左边农民
        if (phase.getPhaseData().isIfEnd()) {
            setPhaseSuccess(GameRegularState.Playing.getValue());
        }
        logger.append("on play");
    }

    public void OnWin(GameRegularState from, GameRegularState to, GameRegularEvent event, GameModel context) throws
            Exception {
        GameRegularModel gameModel = getGameModel();
        RegularPlayPhaseModel phase = (RegularPlayPhaseModel) gameModel.getPhase(GameRegularState.Playing.getValue());
        RegularPlayPhaseDataItem lastDataItem = phase.getPhaseData().getLastDataItem();
//        boolean isAuto = lastDataItem.isAuto();
//        List<Integer> playCards = new ArrayList<>(lastDataItem.getShowCards());
//        int placeRole = lastDataItem.getNowPlace(); //玩家角色 1地主 2右边农民 3左边农民
        if (phase.getPhaseData().isIfEnd()) {
            setPhaseSuccess(GameRegularState.Playing.getValue());
        }
        logger.append("on win");
    }

    public void OnLost(GameRegularState from, GameRegularState to, GameRegularEvent event, GameModel context) throws
            Exception {
        GameRegularModel gameModel = getGameModel();
        RegularPlayPhaseModel phase = (RegularPlayPhaseModel) gameModel.getPhase(GameRegularState.Playing.getValue());
        RegularPlayPhaseDataItem lastDataItem = phase.getPhaseData().getLastDataItem();
//        boolean isAuto = lastDataItem.isAuto();
//        List<Integer> playCards = new ArrayList<>(lastDataItem.getShowCards());
//        int placeRole = lastDataItem.getNowPlace(); //玩家角色 1地主 2右边农民 3左边农民
        if (phase.getPhaseData().isIfEnd()) {
            setPhaseSuccess(GameRegularState.Playing.getValue());
        }
        logger.append("on lost");
    }

    public void OnGuessSize(GameRegularState from, GameRegularState to, GameRegularEvent event,
                            GameModel context) throws Exception {
        GameRegularModel gameModel = getGameModel();
        GuessSizePhaseModel phase = (GuessSizePhaseModel) gameModel.getPhase(GameRegularState.GuessSize.getValue());
        GuessSizePhaseData phaseData = phase.getPhaseData();

        GuessSizeBetService betService = SpringIocUtil.getBean(GuessSizeBetService.class);
        TicketResult betResult = betService.betAndQueryPrizeLevel(gameModel.getUserName(), phase.getGameInstanceId());
        RegularPlayPhaseModel phaseModel = (RegularPlayPhaseModel) gameModel.getPhase(GameRegularState.Playing
                .getValue());
        boolean flag = phaseModel.getPhaseData().isWin() && phaseModel.getPhaseData().getCurrentBombNumbers() == 0;
        betResult.setPrizeLevel(flag ? 1 : 0);


        BetPhaseModel betPhaseModel = (BetPhaseModel) gameModel.getPhase(GameRegularState.Bet.getValue());
        RaisePhaseModel raisePhaseModel = (RaisePhaseModel) gameModel.getPhase(GameRegularState.Raise.getValue());
        float totalMoney = 0.0f;
        if (!flag) {//如果不翻牌
            if (phaseModel.getPhaseData().isWin()) {
                Integer currentBombNumbers = phaseModel.getPhaseData().getCurrentBombNumbers();
                if (currentBombNumbers == 3) {
                    totalMoney = 10;
                } else if (currentBombNumbers == 2) {
                    totalMoney = 3;
                } else if (currentBombNumbers == 1) {
                    totalMoney = 2;
                } else {
                    totalMoney = 0;
                }
            }
        } else {
            if(raisePhaseModel.getPhaseData().getTicketResult().getPrizeLevel()==5) {
                totalMoney = 0.0f;
            }else{
                totalMoney = 1.5f;
            }
        }
        BigDecimal lastTotalMoney = new BigDecimal(String.valueOf(betPhaseModel.getPhaseData().getBetAmt())).multiply(new
                BigDecimal
                (String.valueOf(totalMoney))).multiply(new BigDecimal(String.valueOf(raisePhaseModel.getPhaseData().getTimes
                ())));
        betResult.setPrizeCash(lastTotalMoney);

        if (StringUtils.isEmpty(betResult.getTicketId())) {
            throw new BetErrorException("下注失败,请重试");
        }
        phaseData.setBetSerialNo(betResult.getTicketId());
        phaseData.setTicketResult(betResult);
        setPhaseSuccess(GameRegularState.GuessSize.getValue());
        logger.append("on raise");
    }

    public void OnGameOver(GameRegularState from, GameRegularState to, GameRegularEvent event,
                           GameModel context) throws Exception {
        RegularClearPhaseData phaseData = (RegularClearPhaseData) getPhaseData(GameRegularState.GameOver.getValue
                ());
        GameRegularModel gameModel = getGameModel();

        RaisePhaseModel phase = (RaisePhaseModel) gameModel.getPhase(GameRegularState.Raise.getValue());
        RaisePhaseData raisePhaseData = phase.getPhaseData();
//        TicketResult betResult = moneyBetService.betAndQueryPrizeLevel(this.getGameType(), gameModel.getUserName(),
//                phaseData.getBetAmt() * (phaseData.getTimes() - 1),
//                phase.getGameInstanceId());
        RegularLastBetService lastBetService = SpringIocUtil.getBean(RegularLastBetService.class);
        TicketResult ticketResult = lastBetService.betAndQueryPrizeLevel(GameTypes.Regular.getValue(), gameModel
                .getUserName(), raisePhaseData.getBetAmt() * (raisePhaseData.getTimes()), gameModel
                .getGameInstanceId());
        if (StringUtils.isEmpty(ticketResult.getTicketId())) {
            throw new BetErrorException("下注失败,请重试");
        }

        BigDecimal totalMoney = ticketResult.getPrizeCash();
        phaseData.setSerialNo(ticketResult.getTicketId()).setTotalMoney(totalMoney);
        setPhaseSuccess(GameRegularState.GameOver.getValue());
        logger.append("game over");
    }

    @Override
    protected void afterTransitionCausedException(GameRegularState fromState, GameRegularState toState,
                                                  GameRegularEvent event, GameModel context) {
        //super.afterTransitionCausedException(fromState, toState, event, context);
        if (getLastException().getTargetException() != null) {
            logger.append(getLastException().getTargetException());
            LOGGER.error("状态转换时出错,from:{},to:{},event:{},context:{}", fromState, toState, event, context);
            this.setStatus(StateMachineStatus.IDLE);
        }

        throw getLastException();
    }

    @Override
    protected void beforeActionInvoked(GameRegularState from, GameRegularState to, GameRegularEvent event, GameModel
            context) {
    }

    @Override
    protected void afterTransitionCompleted(GameRegularState fromState, GameRegularState toState, GameRegularEvent
            event, GameModel context) throws Exception {
        GameRegularModel gameModel = getGameModel();
        GameManager bean = SpringIocUtil.getBean(GameManager.class);
        bean.saveGameModelToCacheAndAsyncDb(gameModel);
        if (toState == GameRegularState.GameOver) {
            GameManager.onGameOver(getGameModel().getUserName());
            bean.clearGameModelFromCache(gameModel.getUserName());
        }
    }


    @Override
    public Integer getGameType() {
        return GameTypes.Regular.getValue();
    }
}
