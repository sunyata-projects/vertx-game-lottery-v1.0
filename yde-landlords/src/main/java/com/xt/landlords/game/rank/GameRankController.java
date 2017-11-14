package com.xt.landlords.game.rank;

import com.xt.landlords.GameManager;
import com.xt.landlords.game.phase.BetPhaseData;
import com.xt.landlords.game.phase.TicketResult;
import com.xt.landlords.game.rank.phase.RankClearPhaseData;
import com.xt.landlords.game.rank.phase.RankPlayPhaseModel;
import com.xt.landlords.ioc.SpringIocUtil;
import com.xt.landlords.service.MoneyBetService;
import com.xt.landlords.statemachine.GameController;
import com.xt.yde.GameTypes;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.squirrelframework.foundation.fsm.StateMachineStatus;
import org.squirrelframework.foundation.fsm.annotation.*;
import org.sunyata.octopus.model.GameModel;

import java.util.List;

/**
 * Created by leo on 17/4/26.
 */

@States({
        @State(name = "Init", initialState = true),
        @State(name = "Bet"),
        @State(name = "Deal"),
        @State(name = "Playing"),
        @State(name = "Turn"),
        @State(name = "GameOver"),
})
@Transitions({
        @Transit(from = "Init", to = "Bet", on = "Bet", callMethod = "OnBet"),
        @Transit(from = "Bet", to = "Deal", on = "Deal", callMethod = "OnDeal"),
        @Transit(from = "Deal", to = "Playing", on = "Play", callMethod = "OnPlay"),
        @Transit(from = "Playing", to = "Playing", on = "Play", callMethod = "OnPlay"),
        @Transit(from = "Playing", to = "Turn", on = "Turn", callMethod = "OnTurn"),
        @Transit(from = "Turn", to = "Deal", on = "Deal", callMethod = "OnDeal"),
        @Transit(from = "Turn", to = "GameOver", on = "GameOver", callMethod = "OnGameOver")
})

@StateMachineParameters(stateType = GameRankState.class, eventType = GameRankEvent.class, contextType =
        GameModel.class)
public class GameRankController extends GameController<GameRankModel, GameRankController, GameRankState,
        GameRankEvent,
        GameModel> {
    private Logger LOGGER = LoggerFactory.getLogger(GameRankController.class);

    public void OnBet(GameRankState from, GameRankState to, GameRankEvent event,
                      GameModel context) throws Exception {
        MoneyBetService moneyBetService = SpringIocUtil.getBean(MoneyBetService.class);

        GameRankModel gameModel = getGameModel();
        BetPhaseData phaseData = (BetPhaseData) getPhaseData(GameRankState.Bet.getValue());
        //下注
        List<TicketResult> ticketResult = moneyBetService.betAndQueryPrizeLevelWithMutiple(GameTypes.Rank.getValue(),
                gameModel
                        .getUserName(), phaseData
                        .getBetAmt(), gameModel.getGameInstanceId());
        phaseData.setBetSerialNo("ticketId");
        for (int i = 0; i < ticketResult.size(); i++)
            phaseData.setTicketResult(ticketResult.get(i));
        setPhaseSuccess(GameRankState.Bet.getValue());
        //LOGGER.info("排名赛中奖倍数:{}", ticketResult.getPrizeLevel());
    }


    //首次发牌
    public void OnDeal(GameRankState from, GameRankState to, GameRankEvent event, GameModel context) throws
            Exception {
        setPhaseSuccess(GameRankState.Deal.getValue());
    }


    public void OnPlay(GameRankState from, GameRankState to, GameRankEvent event, GameModel context) throws
            Exception {
        GameRankModel gameModel = getGameModel();
        RankPlayPhaseModel phase = gameModel.getLastPlayPhaseModel();
        if (phase.getPhaseData().isIfEnd()) {
            setPhaseSuccess(GameRankState.Playing.getValue());
        }
    }


    public void OnTurn(GameRankState from, GameRankState to, GameRankEvent event, GameModel context) throws
            Exception {
        setPhaseSuccess(GameRankState.Turn.getValue());

    }


    public void OnGameOver(GameRankState from, GameRankState to, GameRankEvent event,
                           GameModel context) throws Exception {
        RankClearPhaseData phaseData = (RankClearPhaseData) getPhaseData(GameRankState.GameOver.getValue
                ());
//        GameRankModel gameModel = getGameModel();

        //GamePhaseModel phase = gameModel.getPhase(GameRankState.Bet.getValue());
//        BetPhaseData betPhaseData = (BetPhaseData) getPhaseData(GameRankState.Bet.getValue());
//        MoneyBetService lastBetService = SpringIocUtil.getBean(MoneyBetService.class);
//        TicketResult ticketResult = lastBetService.betAndQueryPrizeLevel(GameTypes.Rank.getValue(), gameModel
//                .getUserName(), betPhaseData.getBetAmt(), gameModel.getGameInstanceId());

//        if (StringUtils.isEmpty(ticketResult.getTicketId())) {
//            throw new BetErrorException("下注失败,请重试");
//        }
        //float totalMoney = ticketResult.getPrizeCash();
//        BigDecimal totalMoney = betPhaseData.getTicketResult().getPrizeCash();
//        phaseData.setSerialNo(betPhaseData.getTicketResult().getTicketId()).setTotalMoney(totalMoney);
        setPhaseSuccess(GameRankState.GameOver.getValue());
    }

    @Override
    protected void afterTransitionCausedException(GameRankState fromState, GameRankState toState,
                                                  GameRankEvent event, GameModel context) {
        //super.afterTransitionCausedException(fromState, toState, event, context);
        if (getLastException().getTargetException() != null) {
            LOGGER.error(ExceptionUtils.getStackTrace(getLastException().getTargetException()));
            LOGGER.error("状态转换时出错,from:{},to:{},event:{},context:{}", fromState, toState, event, context);
            this.setStatus(StateMachineStatus.IDLE);
        }

        throw getLastException();
    }

    @Override
    protected void beforeActionInvoked(GameRankState from, GameRankState to, GameRankEvent event, GameModel
            context) {
    }

    @Override
    protected void afterTransitionCompleted(GameRankState fromState, GameRankState toState, GameRankEvent
            event, GameModel context) throws Exception {
        GameRankModel gameModel = getGameModel();
        GameManager bean = SpringIocUtil.getBean(GameManager.class);
        bean.saveGameModelToCacheAndAsyncDb(gameModel);
        if (toState == GameRankState.GameOver) {
            GameManager.onGameOver(getGameModel().getUserName());
            bean.clearGameModelFromCache(gameModel.getUserName());
        }
    }


    @Override
    public Integer getGameType() {
        return GameTypes.Rank.getValue();
    }
}
