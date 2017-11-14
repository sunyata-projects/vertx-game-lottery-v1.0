package com.xt.landlords.game.crazy;

import com.xt.landlords.GameManager;
import com.xt.landlords.account.Account;
import com.xt.landlords.game.crazy.phase.CrazyClearPhaseData;
import com.xt.landlords.game.crazy.phase.CrazyDragPhaseModel;
import com.xt.landlords.game.crazy.phase.CrazyGuessSizePhaseDataItem;
import com.xt.landlords.game.crazy.phase.CrazyGuessSizePhaseModel;
import com.xt.landlords.game.phase.BetPhaseData;
import com.xt.landlords.game.phase.BetPhaseModel;
import com.xt.landlords.game.phase.TicketResult;
import com.xt.landlords.ioc.SpringIocUtil;
import com.xt.landlords.service.MoneyBetService;
import com.xt.landlords.statemachine.GameController;
import com.xt.yde.GameTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        @State(name = "Drag"),
        @State(name = "DragOver"),
        @State(name = "GuessSize"),
        @State(name = "GameOver"),
})
@Transitions({
        @Transit(from = "Init", to = "Bet", on = "Bet", callMethod = "OnBet"),
        @Transit(from = "Bet", to = "Deal", on = "Deal", callMethod = "OnDeal"),
        @Transit(from = "Deal", to = "Drag", on = "Drag", callMethod = "OnDrag"),

        @Transit(from = "Drag", to = "Drag", on = "Drag", callMethod = "OnDrag"),
        @Transit(from = "Drag", to = "DragOver", on = "DragOver", callMethod = "OnGragOver"),

        @Transit(from = "DragOver", to = "GuessSize", on = "EnterGuessSize", callMethod = "OnEnterGuessSize"),
        @Transit(from = "DragOver", to = "GameOver", on = "GameOver", callMethod = "OnGameOver"),

        @Transit(from = "GuessSize", to = "GuessSize", on = "GuessSize", callMethod = "OnGuessSize"),
        @Transit(from = "GuessSize", to = "GameOver", on = "GameOver", callMethod = "OnGameOver")
})

@StateMachineParameters(stateType = GameCrazyState.class, eventType = GameCrazyEvent.class, contextType = GameModel
        .class)
public class GameCrazyController extends GameController<GameCrazyModel, GameCrazyController, GameCrazyState,
        GameCrazyEvent,
        GameModel> {
    //    @Autowired
//    private ApplicationContext applicationContext;
    private Logger LOGGER = LoggerFactory.getLogger(GameCrazyController.class);

    public void OnBet(GameCrazyState from, GameCrazyState to, GameCrazyEvent event,
                      GameModel context) throws Exception {
        MoneyBetService moneyBetService = SpringIocUtil.getBean(MoneyBetService.class);

        GameCrazyModel gameModel = getGameModel();
        BetPhaseData phaseData = (BetPhaseData) getPhaseData(GameCrazyState.Bet.getValue());
        //下注
        TicketResult ticketResult = moneyBetService.betAndQueryPrizeLevel(GameTypes.Crazy.getValue(), gameModel
                .getUserName(), phaseData
                .getBetAmt(), gameModel.getGameInstanceId());
        phaseData.setBetSerialNo(ticketResult.getTicketId());
        phaseData.setTicketResult(ticketResult);
        setPhaseSuccess(GameCrazyState.Bet.getValue());
    }


    //首次发牌
    public void OnDeal(GameCrazyState from, GameCrazyState to, GameCrazyEvent event, GameModel context) throws
            Exception {
        setPhaseSuccess(GameCrazyState.Deal.getValue());
    }

    public void OnDragOver(GameCrazyState from, GameCrazyState to, GameCrazyEvent event, GameModel context) throws
            Exception {
        setPhaseSuccess(GameCrazyState.DragOver.getValue());
    }

    public void OnDrag(GameCrazyState from, GameCrazyState to, GameCrazyEvent event, GameModel context) throws
            Exception {
        GameCrazyModel gameModel = getGameModel();
        CrazyDragPhaseModel phase = (CrazyDragPhaseModel) gameModel.getPhase(GameCrazyState.Drag.getValue());
        int size = phase.getPhaseData().getItems().size();
        if (size == 5) {
            setPhaseSuccess(GameCrazyState.Drag.getValue());
        }

    }

    public void OnEnterGuessSize(GameCrazyState from, GameCrazyState to, GameCrazyEvent event,
                                 GameModel context) throws Exception {
        LOGGER.info("OnEnterGuessSize");
    }

    public void OnGuessSize(GameCrazyState from, GameCrazyState to, GameCrazyEvent event,
                            GameModel context) throws Exception {
        GameCrazyModel gameModel = getGameModel();
        CrazyGuessSizePhaseModel phaseModel = (CrazyGuessSizePhaseModel) gameModel.getPhase(GameCrazyState
                .GuessSize.getValue());
        BetPhaseModel phase = (BetPhaseModel) gameModel.getPhase(GameCrazyState.Bet.getValue());
        MoneyBetService moneyBetService = SpringIocUtil.getBean(MoneyBetService.class);
        TicketResult ticketResult = moneyBetService.betAndQueryPrizeLevelForCrazyGuessSize(GameTypes.Crazy.getValue()
                , phaseModel.getType(), phase.getPhaseData().getTicketResult().getPrizeCash(), gameModel.getUserName
                        (), phaseModel.getGuessCount());
        boolean isWin = (int) ticketResult.getPrizeLevel() == 1;
        phaseModel.guess(isWin);
        if (!isWin) {//本次猜结果为输
            setPhaseSuccess(GameCrazyState.GuessSize.getValue());
        }
        if (phaseModel.getGuessCount() == 5) {
            setPhaseSuccess(GameCrazyState.GuessSize.getValue());
        }
    }

    public void OnGameOver(GameCrazyState from, GameCrazyState to, GameCrazyEvent event,
                           GameModel context) throws Exception {
        CrazyClearPhaseData phaseData = (CrazyClearPhaseData) getPhaseData(GameCrazyState.GameOver.getValue
                ());
        GameCrazyModel gameModel = getGameModel();

        //MoneyBetService lastBetService = SpringIocUtil.getBean(MoneyBetService.class);
//        TicketResult ticketResult = lastBetService.betAndQueryPrizeLevel(GameTypes.Crazy.getValue(), gameModel
//                .getUserName(), 0, gameModel
//                .getGameInstanceId());
//        if (StringUtils.isEmpty(ticketResult.getTicketId())) {
//            throw new BetErrorException("下注失败,请重试");
//        }
        BetPhaseModel phase = (BetPhaseModel) gameModel.getPhase(GameCrazyState.Bet.getValue());
        int betAmt = phase.getPhaseData().getBetAmt();

        CrazyGuessSizePhaseModel phaseModel = (CrazyGuessSizePhaseModel) gameModel.getPhase(GameCrazyState
                .GuessSize.getValue());
        if (phaseModel != null) {
            long count = phaseModel.getPhaseData().getItems().stream().filter(p -> !p.isWin()).count();//获取输的次数
            long winCount = phaseModel.getPhaseData().getItems().stream().filter(p -> p.isWin()).count();
            if(count>1){//如果输的次数大于1,则证明最后一次输了
                count = 0;
            }
            CrazyGuessSizePhaseDataItem crazyGuessSizePhaseDataItem = phaseModel.getPhaseData().getItems().get(0);
            int type = crazyGuessSizePhaseDataItem.getType();
            BigDecimal totalMoney = BigDecimal.ZERO;
            BigDecimal prizeCash = phase.getPhaseData().getTicketResult().getPrizeCash();
            if (type == 0) {//全比
                if (count != 0) {
                    totalMoney = prizeCash.multiply(new BigDecimal("2").pow((int) winCount));
                }
            } else {
                BigDecimal halfOfPrizeCash = prizeCash.divide(new BigDecimal("2.0"));
                if (count != 0) {
                    totalMoney = halfOfPrizeCash.multiply(new BigDecimal("2").pow((int) winCount));
                }
                totalMoney = totalMoney.add(halfOfPrizeCash);
            }
            Account.addBalance(gameModel.getUserName(), totalMoney);
            //totalMoney = totalMoney.add(phase.getPhaseData().getTicketResult().getPrizeCash());
            phaseData.setSerialNo("serialNo").setTotalMoney(totalMoney);
        } else {
            BigDecimal totalMoney = phase.getPhaseData().getTicketResult().getPrizeCash();
            phaseData.setSerialNo("serialNo").setTotalMoney(totalMoney);
            Account.addBalance(gameModel.getUserName(), totalMoney);
        }
        setPhaseSuccess(GameCrazyState.GameOver.getValue());
    }

    @Override
    protected void afterTransitionCausedException(GameCrazyState fromState, GameCrazyState toState,
                                                  GameCrazyEvent event, GameModel context) {
        //super.afterTransitionCausedException(fromState, toState, event, context);
        if (getLastException().getTargetException() != null) {
            LOGGER.error("状态转换时出错,from:{},to:{},event:{},context:{}", fromState, toState, event, context);
            this.setStatus(StateMachineStatus.IDLE);
        }

        throw getLastException();
    }

    @Override
    protected void beforeActionInvoked(GameCrazyState from, GameCrazyState to, GameCrazyEvent event, GameModel
            context) {
    }

    @Override
    protected void afterTransitionCompleted(GameCrazyState fromState, GameCrazyState toState, GameCrazyEvent
            event, GameModel context) throws Exception {
        GameCrazyModel gameModel = getGameModel();
        GameManager bean = SpringIocUtil.getBean(GameManager.class);
        bean.saveGameModelToCacheAndAsyncDb(gameModel);
        if (toState == GameCrazyState.GameOver) {
            GameManager.onGameOver(getGameModel().getUserName());
            bean.clearGameModelFromCache(gameModel.getUserName());
        }
    }


    @Override
    public Integer getGameType() {
        return GameTypes.Crazy.getValue();
    }
}
