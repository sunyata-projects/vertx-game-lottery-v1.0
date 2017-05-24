package com.xt.landlords.game.regular;

import com.xt.landlords.BetService;
import com.xt.landlords.GameTypes;
import com.xt.landlords.StoreManager;
import com.xt.landlords.exception.BetErrorException;
import com.xt.landlords.game.phase.BetPhaseData;
import com.xt.landlords.game.regular.condition.GuessSizeCondition;
import com.xt.landlords.game.regular.condition.LoseCondition;
import com.xt.landlords.game.regular.condition.WinCondition;
import com.xt.landlords.ioc.SpringIocUtil;
import com.xt.landlords.statemachine.GameController;
import com.xt.landlords.statemachine.MyCondition;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.squirrelframework.foundation.fsm.HistoryType;
import org.squirrelframework.foundation.fsm.StateMachineStatus;
import org.squirrelframework.foundation.fsm.annotation.*;
import org.sunyata.octopus.model.GameModel;
import org.sunyata.octopus.model.GamePhaseModel;
import org.sunyata.octopus.model.PhaseState;

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
public class GameRegularController extends GameController<GameRegularController, GameRegularState,
        GameRegularEvent,
        GameModel> {
    //    @Autowired
//    private ApplicationContext applicationContext;
    private StringBuilder logger = new StringBuilder();
    private Logger LOGGER = LoggerFactory.getLogger(GameRegularController.class);

    public void OnBet(GameRegularState from, GameRegularState to, GameRegularEvent event,
                      GameModel context) throws Exception {
        BetService betService = SpringIocUtil.getBean(BetService.class);
        GameRegularModel gameModel = (GameRegularModel) context;
        GamePhaseModel phase = gameModel.getPhase(GameRegularPhaseName.Bet.getValue());
        BetPhaseData phaseData = (BetPhaseData) phase.getPhaseData();
        //下注
        String serialNo = betService.bet(gameModel.getUserName(), phaseData.getBetAmt(), gameModel.getGameInstanceId());
        if (StringUtils.isEmpty(serialNo)) {
            throw new BetErrorException("下注失败,请重试");
        }
        phase.setPhaseState(PhaseState.Success);
        phaseData.setBetSerialNo(serialNo);
        StoreManager storeManager = SpringIocUtil.getBean(StoreManager.class);
        storeManager.set(gameModel.getUserName(), "gameModel", gameModel);
        logger.append("on bet");
    }

    public void OnRaise(GameRegularState from, GameRegularState to, GameRegularEvent event,
                        GameModel context) throws Exception {
        BetService betService = SpringIocUtil.getBean(BetService.class);
        GameRegularModel gameModel = (GameRegularModel) context;
        GamePhaseModel phase = gameModel.getPhase(GameRegularPhaseName.Raise.getValue());
        BetPhaseData phaseData = (BetPhaseData) phase.getPhaseData();
        String serialNo = betService.bet(gameModel.getUserName(), phaseData.getBetAmt(), phase.getGameInstanceId());
        if (StringUtils.isEmpty(serialNo)) {
            throw new BetErrorException("下注失败,请重试");
        }
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
        addOptionalDot();
    }

    private void addOptionalDot() {
        if (logger.length() > 0) {
            logger.append('.');
        }
    }

    public String consumeLog() {
        final String result = logger.toString();
        logger = new StringBuilder();
        return result;
    }

    @Override
    public GameRegularEvent getBetEvent() {
        return GameRegularEvent.Bet;
    }

    @Override
    public GameRegularState getInitState() {
        return GameRegularState.Init;
    }

    @Override
    public Integer getGameType() {
        return GameTypes.Regular.getValue();
    }

    static GameRegularController stateMachine;

    public static void main(String[] args) {
//        StateMachineBuilder<GameRegularController, GameRegularState, GameRegularEvent, MyContext> builder =
//                StateMachineBuilderFactory.<GameRegularController, GameRegularState, GameRegularEvent, MyContext>
//                        create(GameRegularController.class, GameRegularState.class,
//                        GameRegularEvent.class, MyContext.class, GameRegularController.class);
////        builder.transit().fromAny().to(GameRegularState.GameOver).on(GameRegularEvent.GameOver).callMethod
////                ("transitFromAnyToAnyOnGameOver");
//        builder.transitions().fromAmong(GameRegularState.Init, GameRegularState.Bet).
//                to(GameRegularState.GameOver).on(GameRegularEvent.GameOver).callMethod("OnGameOver");
//
//        stateMachine = builder.newStateMachine(GameRegularState.Init);

//        stateMachine = GameStateControllerFactory.createGameRegularController(new
//                GameModel(1, ""), GameRegularState.Init);
//
//        GameModel context = new GameModel(1, "dsfs");
//        try {
//            stateMachine.start(context);
//            stateMachine.fire(GameRegularEvent.GameOver, context);
//            System.out.println("current Status:" + stateMachine.getCurrentState());
//
//
//            StateMachineData.Reader<GameRegularController, GameRegularState, GameRegularEvent, GameModel> savedData =
//                    stateMachine.dumpSavedData();
//            String serialize = JsonSerializableSupport.serialize(savedData);
//            StateMachineDataImpl<GameRegularController, GameRegularState, GameRegularEvent, GameModel> reader =
//                    JsonSerializableSupport.deserialize(serialize, StateMachineDataImpl.class);
//            stateMachine.loadSavedData(reader);
//            System.out.println(stateMachine.getCurrentState());
//        } catch (Exception ex) {
//            System.out.println(ExceptionUtils.getFullStackTrace(ex));
//        }
    }
}
