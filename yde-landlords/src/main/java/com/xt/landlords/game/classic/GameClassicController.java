package com.xt.landlords.game.classic;

import com.xt.landlords.GameManager;
import com.xt.landlords.account.Account;
import com.xt.landlords.game.classic.phase.ClassicClearPhaseData;
import com.xt.landlords.game.classic.phase.ClassicGuessSizePhaseDataItem;
import com.xt.landlords.game.classic.phase.ClassicGuessSizePhaseModel;
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
        @State(name = "Playing"),
        @State(name = "Turn"),
        @State(name = "GuessSize"),
        @State(name = "GameOver"),
})
@Transitions({
        @Transit(from = "Init", to = "Bet", on = "Bet", callMethod = "OnBet"),
        @Transit(from = "Bet", to = "Deal", on = "Deal", callMethod = "OnDeal"),
        @Transit(from = "Deal", to = "Playing", on = "Play", callMethod = "OnPlay"),
        @Transit(from = "Playing", to = "Playing", on = "Play", callMethod = "OnPlay"),
        @Transit(from = "Playing", to = "Turn", on = "Turn", callMethod = "OnTurn"),
        @Transit(from = "Turn", to = "GameOver", on = "GameOver", callMethod = "OnGameOver"),
        @Transit(from = "Turn", to = "GuessSize", on = "EnterGuessSize", callMethod = "OnEnterGuessSize"),
        @Transit(from = "GuessSize", to = "GuessSize", on = "GuessSize", callMethod = "OnGuessSize"),
        @Transit(from = "GuessSize", to = "GameOver", on = "GameOver", callMethod = "OnGameOver"),
        @Transit(from = "Deal", to = "GameOver", on = "GameOver", callMethod = "OnGameOver"),
})

@StateMachineParameters(stateType = GameClassicState.class, eventType = GameClassicEvent.class, contextType = GameModel
        .class)
public class GameClassicController extends GameController<GameClassicModel, GameClassicController, GameClassicState,
        GameClassicEvent,
        GameModel> {
    //    @Autowired
//    private ApplicationContext applicationContext;
    private Logger LOGGER = LoggerFactory.getLogger(GameClassicController.class);

    public void OnBet(GameClassicState from, GameClassicState to, GameClassicEvent event,
                      GameModel context) throws Exception {
        MoneyBetService moneyBetService = SpringIocUtil.getBean(MoneyBetService.class);

        GameClassicModel gameModel = getGameModel();
        BetPhaseData phaseData = (BetPhaseData) getPhaseData(GameClassicState.Bet.getValue());
        //下注
        TicketResult ticketResult = moneyBetService.betAndQueryPrizeLevel(GameTypes.Classic.getValue(), gameModel
                .getUserName(), phaseData
                .getBetAmt(), gameModel.getGameInstanceId());
        phaseData.setBetSerialNo(ticketResult.getTicketId());
        phaseData.setTicketResult(ticketResult);
        setPhaseSuccess(GameClassicState.Bet.getValue());
    }


    //首次发牌
    public void OnDeal(GameClassicState from, GameClassicState to, GameClassicEvent event, GameModel context) throws
            Exception {
        setPhaseSuccess(GameClassicState.Deal.getValue());
    }

//    public void OnDragOver(GameClassicState from, GameClassicState to, GameClassicEvent event, GameModel context)
// throws
//            Exception {
//        setPhaseSuccess(GameClassicState.DragOver.getValue());
//    }
//
//    public void OnDrag(GameClassicState from, GameClassicState to, GameClassicEvent event, GameModel context) throws
//            Exception {
//        GameClassicModel gameModel = getGameModel();
//        ClassicDragPhaseModel phase = (ClassicDragPhaseModel) gameModel.getPhase(GameClassicState.Drag.getValue());
//        int size = phase.getPhaseData().getItems().size();
//        if (size == 5) {
//            setPhaseSuccess(GameClassicState.Drag.getValue());
//        }
//
//    }

    public void OnEnterGuessSize(GameClassicState from, GameClassicState to, GameClassicEvent event,
                                 GameModel context) throws Exception {
        LOGGER.info("OnEnterGuessSize");
    }

    public void OnGuessSize(GameClassicState from, GameClassicState to, GameClassicEvent event,
                            GameModel context) throws Exception {
        GameClassicModel gameModel = getGameModel();
        ClassicGuessSizePhaseModel phaseModel = (ClassicGuessSizePhaseModel) gameModel.getPhase(GameClassicState
                .GuessSize.getValue());
        BetPhaseModel phase = (BetPhaseModel) gameModel.getPhase(GameClassicState.Bet.getValue());
        MoneyBetService moneyBetService = SpringIocUtil.getBean(MoneyBetService.class);
        TicketResult ticketResult = moneyBetService.betAndQueryPrizeLevelForCrazyGuessSize(GameTypes.Classic.getValue
                (), phaseModel.getType(), phase.getPhaseData().getTicketResult().getPrizeCash(), gameModel
                .getUserName(), phaseModel.getGuessCount());
        boolean isWin = (int) ticketResult.getPrizeLevel() == 1;
        phaseModel.guess(isWin);
        if (!isWin) {//本次猜结果为输
            setPhaseSuccess(GameClassicState.GuessSize.getValue());
        }
        if (phaseModel.getGuessCount() == 5) {
            setPhaseSuccess(GameClassicState.GuessSize.getValue());
        }
    }

    public void OnGameOver(GameClassicState from, GameClassicState to, GameClassicEvent event,
                           GameModel context) throws Exception {
        ClassicClearPhaseData phaseData = (ClassicClearPhaseData) getPhaseData(GameClassicState.GameOver.getValue
                ());
        GameClassicModel gameModel = getGameModel();

        //MoneyBetService lastBetService = SpringIocUtil.getBean(MoneyBetService.class);
//        TicketResult ticketResult = lastBetService.betAndQueryPrizeLevel(GameTypes.Classic.getValue(), gameModel
//                .getUserName(), 0, gameModel
//                .getGameInstanceId());
//        if (StringUtils.isEmpty(ticketResult.getTicketId())) {
//            throw new BetErrorException("下注失败,请重试");
//        }
        BetPhaseModel phase = (BetPhaseModel) gameModel.getPhase(GameClassicState.Bet.getValue());
        int betAmt = phase.getPhaseData().getBetAmt();

        ClassicGuessSizePhaseModel phaseModel = (ClassicGuessSizePhaseModel) gameModel.getPhase(GameClassicState
                .GuessSize.getValue());
        if (phaseModel != null) {
            long count = phaseModel.getPhaseData().getItems().stream().filter(p -> !p.isWin()).count();
            long winCount = phaseModel.getPhaseData().getItems().stream().filter(p -> p.isWin()).count();
            if(count>1){//如果输的次数大于1,则证明最后一次输了
                count = 0;
            }
            ClassicGuessSizePhaseDataItem ClassicGuessSizePhaseDataItem = phaseModel.getPhaseData().getItems().get(0);
            int type = ClassicGuessSizePhaseDataItem.getType();
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
        setPhaseSuccess(GameClassicState.GameOver.getValue());
    }

    @Override
    protected void afterTransitionCausedException(GameClassicState fromState, GameClassicState toState,
                                                  GameClassicEvent event, GameModel context) {
        //super.afterTransitionCausedException(fromState, toState, event, context);
        if (getLastException().getTargetException() != null) {
            LOGGER.error("状态转换时出错,from:{},to:{},event:{},context:{}", fromState, toState, event, context);
            this.setStatus(StateMachineStatus.IDLE);
        }

        throw getLastException();
    }

    @Override
    protected void beforeActionInvoked(GameClassicState from, GameClassicState to, GameClassicEvent event, GameModel
            context) {
    }

    @Override
    protected void afterTransitionCompleted(GameClassicState fromState, GameClassicState toState, GameClassicEvent
            event, GameModel context) throws Exception {
        GameClassicModel gameModel = getGameModel();
        GameManager bean = SpringIocUtil.getBean(GameManager.class);
        bean.saveGameModelToCacheAndAsyncDb(gameModel);
        if (toState == GameClassicState.GameOver) {
            GameManager.onGameOver(getGameModel().getUserName());
            bean.clearGameModelFromCache(gameModel.getUserName());
        }
    }


    @Override
    public Integer getGameType() {
        return GameTypes.Classic.getValue();
    }
}
