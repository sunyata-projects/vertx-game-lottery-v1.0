package com.xt.landlords.game.mission;

import com.xt.landlords.GameManager;
import com.xt.landlords.GameTypes;
import com.xt.landlords.exception.BetErrorException;
import com.xt.landlords.game.mission.condition.DealCondition;
import com.xt.landlords.game.mission.condition.LoseCondition;
import com.xt.landlords.game.mission.condition.PlayingCondition;
import com.xt.landlords.game.mission.condition.WinCondition;
import com.xt.landlords.game.mission.phase.MissionClearPhaseData;
import com.xt.landlords.game.mission.phase.MissionPlayPhaseModel;
import com.xt.landlords.game.phase.BetPhaseData;
import com.xt.landlords.game.phase.TicketResult;
import com.xt.landlords.ioc.SpringIocUtil;
import com.xt.landlords.service.MoneyBetService;
import com.xt.landlords.statemachine.GameController;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.squirrelframework.foundation.fsm.HistoryType;
import org.squirrelframework.foundation.fsm.StateMachineStatus;
import org.squirrelframework.foundation.fsm.TransitionType;
import org.squirrelframework.foundation.fsm.annotation.*;
import org.sunyata.octopus.model.GameModel;

/**
 * Created by leo on 17/4/26.
 */

@States({
        @State(name = "Init", initialState = true),
        @State(name = "Bet"),
        @State(name = "Deal"),
        @State(name = "Playing"),
        @State(name = "PlayEnd", historyType = HistoryType.DEEP),
        @State(parent = "PlayEnd", name = "Win"),
        @State(parent = "PlayEnd", name = "Lose"),
        @State(name = "GameOver"),
})
@Transitions({
        @Transit(from = "Init", to = "Bet", on = "Bet", callMethod = "OnBet"),
        @Transit(from = "Bet", to = "Deal", on = "Deal", callMethod = "OnDeal"),
        @Transit(from = "Deal", to = "Playing", on = "Play", callMethod = "OnPlay"),
        @Transit(from = "Playing", to = "Playing", on = "Play", callMethod = "OnPlay",type = TransitionType.INTERNAL,when =PlayingCondition.class),
        @Transit(from = "Playing", to = "Win", on = "Play", callMethod = "OnWin",when = WinCondition.class),
        @Transit(from = "Playing", to = "Lose", on = "Play", callMethod = "OnLose",when = LoseCondition.class),
        @Transit(from = "Win", to = "Deal", on = "Deal", callMethod = "OnDeal", when = DealCondition.class),
        @Transit(from = "Win", to = "GameOver", on = "GameOver", callMethod = "OnGameOver"),
        @Transit(from = "Lose", to = "GameOver", on = "GameOver", callMethod = "OnGameOver"),
})

//@ContextEvent(finishEvent = "OnLuckDraw")
@StateMachineParameters(stateType = GameMissionState.class, eventType = GameMissionEvent.class, contextType =
        GameModel.class)
//@Configurable(preConstruction = true)
public class GameMissionController extends GameController<GameMissionModel, GameMissionController, GameMissionState,
        GameMissionEvent,
        GameModel> {
    //    @Autowired
//    private ApplicationContext applicationContext;
    private StringBuilder logger = new StringBuilder();
    private Logger LOGGER = LoggerFactory.getLogger(GameMissionController.class);

    public void OnBet(GameMissionState from, GameMissionState to, GameMissionEvent event,
                      GameModel context) throws Exception {
        MoneyBetService moneyBetService = SpringIocUtil.getBean(MoneyBetService.class);

        GameMissionModel gameModel = getGameModel();
        BetPhaseData phaseData = (BetPhaseData) getPhaseData(GameMissionState.Bet.getValue());
        //下注
        TicketResult ticketResult = moneyBetService.betAndQueryPrizeLevel(GameTypes.Mission.getValue(), gameModel
                .getUserName(), phaseData
                .getBetAmt(), gameModel.getGameInstanceId());
        phaseData.setBetSerialNo(ticketResult.getTicketId());
        phaseData.setTicketResult(ticketResult);
        setPhaseSuccess(GameMissionState.Bet.getValue());
        logger.append("on bet");
    }


    //首次发牌
    public void OnDeal(GameMissionState from, GameMissionState to, GameMissionEvent event, GameModel context) throws
            Exception {
//        setPhaseSuccess(GameMissionState.Deal.getValue());
        logger.append("on raise");
    }


    public void OnPlay(GameMissionState from, GameMissionState to, GameMissionEvent event, GameModel context) throws
            Exception {
        GameMissionModel gameModel = getGameModel();
        MissionPlayPhaseModel phase = gameModel.getLastPlayPhaseModel();
        //MissionPlayPhaseDataItem lastDataItem = phase.getPhaseData().getLastDataItem();
//        boolean isAuto = lastDataItem.isAuto();
//        List<Integer> playCards = new ArrayList<>(lastDataItem.getShowCards());
//        int placeRole = lastDataItem.getNowPlace(); //玩家角色 1地主 2右边农民 3左边农民
        if (phase.getPhaseData().isIfEnd()) {
            setPhaseSuccess(GameMissionState.Play.getValue());
        }
        logger.append("on play");
    }

    public void OnWin(GameMissionState from, GameMissionState to, GameMissionEvent event, GameModel context) throws
            Exception {
        GameMissionModel gameModel = getGameModel();
        MissionPlayPhaseModel phase = (MissionPlayPhaseModel) gameModel.getLastPlayPhaseModel();
        //MissionPlayPhaseDataItem lastDataItem = phase.getPhaseData().getLastDataItem();
//        boolean isAuto = lastDataItem.isAuto();
//        List<Integer> playCards = new ArrayList<>(lastDataItem.getShowCards());
//        int placeRole = lastDataItem.getNowPlace(); //玩家角色 1地主 2右边农民 3左边农民
        if (phase.getPhaseData().isIfEnd()) {
            setPhaseSuccess(GameMissionState.Play.getValue());
        }
        logger.append("on win");
    }

    public void OnLost(GameMissionState from, GameMissionState to, GameMissionEvent event, GameModel context) throws
            Exception {
        GameMissionModel gameModel = getGameModel();
        MissionPlayPhaseModel phase = (MissionPlayPhaseModel) gameModel.getLastPlayPhaseModel();
        //MissionPlayPhaseDataItem lastDataItem = phase.getPhaseData().getLastDataItem();
//        boolean isAuto = lastDataItem.isAuto();
//        List<Integer> playCards = new ArrayList<>(lastDataItem.getShowCards());
//        int placeRole = lastDataItem.getNowPlace(); //玩家角色 1地主 2右边农民 3左边农民
        if (phase.getPhaseData().isIfEnd()) {
            setPhaseSuccess(GameMissionState.Play.getValue());
        }
        logger.append("on lost");
    }


    public void OnGameOver(GameMissionState from, GameMissionState to, GameMissionEvent event,
                           GameModel context) throws Exception {
        MissionClearPhaseData phaseData = (MissionClearPhaseData) getPhaseData(GameMissionState.GameOver.getValue
                ());
        GameMissionModel gameModel = getGameModel();

        MoneyBetService lastBetService = SpringIocUtil.getBean(MoneyBetService.class);
        TicketResult ticketResult = lastBetService.betAndQueryPrizeLevel(GameTypes.Mission.getValue(), gameModel
                .getUserName(), 0, gameModel
                .getGameInstanceId());
        if (StringUtils.isEmpty(ticketResult.getTicketId())) {
            throw new BetErrorException("下注失败,请重试");
        }
        int totalMoney = ticketResult.getPrizeCash();
        phaseData.setSerialNo(ticketResult.getTicketId()).setTotalMoney(totalMoney);
        setPhaseSuccess(GameMissionState.GameOver.getValue());
        logger.append("game over");
    }

    @Override
    protected void afterTransitionCausedException(GameMissionState fromState, GameMissionState toState,
                                                  GameMissionEvent event, GameModel context) {
        //super.afterTransitionCausedException(fromState, toState, event, context);
        if (getLastException().getTargetException() != null) {
            LOGGER.error(ExceptionUtils.getStackTrace(getLastException().getTargetException()));
            LOGGER.error("状态转换时出错,from:{},to:{},event:{},context:{}", fromState, toState, event, context);
            this.setStatus(StateMachineStatus.IDLE);
        }

        throw getLastException();
    }

    @Override
    protected void beforeActionInvoked(GameMissionState from, GameMissionState to, GameMissionEvent event, GameModel
            context) {
    }

    @Override
    protected void afterTransitionCompleted(GameMissionState fromState, GameMissionState toState, GameMissionEvent
            event, GameModel context) throws Exception {
        GameMissionModel gameModel = getGameModel();
        GameManager bean = SpringIocUtil.getBean(GameManager.class);
        bean.saveGameModelToCacheAndAsyncDb(gameModel);
        if (toState == GameMissionState.GameOver) {
            GameManager.onGameOver(getGameModel().getUserName());
            bean.clearGameModelFromCache(gameModel.getUserName());
        }
    }


    @Override
    public Integer getGameType() {
        return GameTypes.Mission.getValue();
    }
}
