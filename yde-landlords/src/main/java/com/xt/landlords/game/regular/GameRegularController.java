package com.xt.landlords.game.regular;

import com.xt.landlords.GameManager;
import com.xt.landlords.GameTypes;
import com.xt.landlords.exception.BetErrorException;
import com.xt.landlords.game.phase.BetPhaseData;
import com.xt.landlords.game.phase.TicketResult;
import com.xt.landlords.game.regular.condition.GuessSizeCondition;
import com.xt.landlords.game.regular.condition.LoseCondition;
import com.xt.landlords.game.regular.condition.WinCondition;
import com.xt.landlords.game.regular.phase.RaisePhaseData;
import com.xt.landlords.game.regular.phase.RaisePhaseModel;
import com.xt.landlords.ioc.SpringIocUtil;
import com.xt.landlords.service.MoneyBetService;
import com.xt.landlords.statemachine.GameController;
import com.xt.landlords.statemachine.MyCondition;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.squirrelframework.foundation.fsm.HistoryType;
import org.squirrelframework.foundation.fsm.StateMachineStatus;
import org.squirrelframework.foundation.fsm.annotation.*;
import org.sunyata.octopus.model.GameModel;

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

        @Transit(from = "Playing", to = "Playing", on = "Play", callMethod = "OnPlay"),
        @Transit(from = "Playing", to = "Win", on = "Play", callMethod = "OnWin", when = WinCondition
                .class),
        @Transit(from = "Playing", to = "Lose", on = "Play", callMethod = "OnLose", when = LoseCondition
                .class),
        @Transit(from = "Win", to = "GuessSize", on = "GuessSize", callMethod = "OnGuessSize", when = GuessSizeCondition
                .class),
        @Transit(from = "Win", to = "LuckDraw", on = "LuckDraw", callMethod = "OnLuckDraw", when = MyCondition
                .class),
        @Transit(from = "Lose", to = "LuckDraw", on = "LuckDraw", callMethod = "OnLuckDraw", when = MyCondition
                .class),
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
        GameRegularModel gameModel = (GameRegularModel) context;
        RaisePhaseModel phase = (RaisePhaseModel) gameModel.getPhase(GameRegularState.Raise.getValue());
        RaisePhaseData phaseData = phase.getPhaseData();
        TicketResult betResult = moneyBetService.betAndQueryPrizeLevel(this.getGameType(), gameModel.getUserName(),
                phaseData
                        .getBetAmt(),
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

    public void OnGameOver(GameRegularState from, GameRegularState to, GameRegularEvent event,
                           GameModel context) throws Exception {
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
            bean.clearGameModelFromCache(gameModel.getUserName());
        }
    }


    @Override
    public Integer getGameType() {
        return GameTypes.Regular.getValue();
    }
}
