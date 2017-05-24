package com.xt.landlords.game.puzzle;

import com.xt.landlords.BetService;
import com.xt.landlords.GameManager;
import com.xt.landlords.GameTypes;
import com.xt.landlords.StoreManager;
import com.xt.landlords.exception.BetErrorException;
import com.xt.landlords.game.phase.BetPhaseData;
import com.xt.landlords.ioc.SpringIocUtil;
import com.xt.landlords.statemachine.GameController;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        @State(name = "GameOver"),
})
@Transitions({
        @Transit(from = "Init", to = "Bet", on = "Bet", callMethod = "OnBet"),
        @Transit(from = "Bet", to = "Deal", on = "Deal", callMethod = "OnDeal"),
        @Transit(from = "Deal", to = "GameOver", on = "GameOver", callMethod = "OnGameOver")
})

//@ContextEvent(finishEvent = "OnLuckDraw")
@StateMachineParameters(stateType = GamePuzzleState.class, eventType = GamePuzzleEvent.class, contextType =
        GameModel.class)
//@Configurable(preConstruction = true)
public class GamePuzzleController extends GameController<GamePuzzleController, GamePuzzleState,
        GamePuzzleEvent,
        GameModel> {
    //    @Autowired
//    private ApplicationContext applicationContext;
    private StringBuilder logger = new StringBuilder();
    private Logger LOGGER = LoggerFactory.getLogger(GamePuzzleController.class);

    public void OnBet(GamePuzzleState from, GamePuzzleState to, GamePuzzleEvent event,
                      GameModel context) throws Exception {
        BetService betService = SpringIocUtil.getBean(BetService.class);
        GamePuzzleModel gameModel = (GamePuzzleModel) context;
        GamePhaseModel phase = gameModel.getPhase(GamePuzzleState.Bet.getValue());
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
        GameManager bean = SpringIocUtil.getBean(GameManager.class);
        bean.syncGameModel(gameModel);
        logger.append("on bet");
    }

    public void OnDeal(GamePuzzleState from, GamePuzzleState to, GamePuzzleEvent event,
                       GameModel context) throws Exception {
        GamePhaseModel phase = context.getPhase(GamePuzzleState.Deal.getValue());
        if (phase == null) {
            throw new Exception("没有找到阶段初始化数据");
        }
        phase.setPhaseState(PhaseState.Success);
        StoreManager storeManager = SpringIocUtil.getBean(StoreManager.class);
        storeManager.set(context.getUserName(), "gameModel", context);
        logger.append("on bet");
    }

    public void OnGameOver(GamePuzzleState from, GamePuzzleState to, GamePuzzleEvent event,
                           GameModel context) throws Exception {
        //todo 调用兑奖接口

        GamePhaseModel phase = context.getPhase(GamePuzzleState.GameOver.getValue());
        if (phase == null) {
            throw new Exception("没有找到阶段初始化数据");
        }
        phase.setPhaseState(PhaseState.Success);

        StoreManager storeManager = SpringIocUtil.getBean(StoreManager.class);
        storeManager.storeGameModel(context.getUserName(), null);

        GameManager.onGameOver(context.getUserName());
        GameManager bean = SpringIocUtil.getBean(GameManager.class);
        bean.syncGameModel(context);
        logger.append("game over");
    }

    @Override
    protected void afterTransitionCausedException(GamePuzzleState fromState, GamePuzzleState toState,
                                                  GamePuzzleEvent event, GameModel context) {
        //super.afterTransitionCausedException(fromState, toState, event, context);
        if (getLastException().getTargetException() != null) {
            logger.append(getLastException().getTargetException());
            LOGGER.error("状态转换时出错,from:{},to:{},event:{},context:{}", fromState, toState, event, context);
            this.setStatus(StateMachineStatus.IDLE);
        }

        throw getLastException();
    }

    @Override
    protected void beforeActionInvoked(GamePuzzleState from, GamePuzzleState to, GamePuzzleEvent event, GameModel
            context) {
//        addOptionalDot();
    }

    @Override
    public GamePuzzleEvent getBetEvent() {
        return GamePuzzleEvent.Bet;
    }

    @Override
    public GamePuzzleState getInitState() {
        return GamePuzzleState.Init;
    }

    @Override
    public Integer getGameType() {
        return GameTypes.Puzzle.getValue();
    }

//    private void addOptionalDot() {
//        if (logger.length() > 0) {
//            logger.append('.');
//        }
//    }
//
//    public String consumeLog() {
//        final String result = logger.toString();
//        logger = new StringBuilder();
//        return result;
//    }

    //static GamePuzzleController stateMachine;

    public static void main(String[] args) {

    }
}
