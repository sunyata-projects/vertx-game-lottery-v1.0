package com.xt.landlords.game.puzzle;

import com.xt.landlords.game.phase.TicketResult;
import com.xt.landlords.service.MoneyBetService;
import com.xt.landlords.GameManager;
import com.xt.landlords.GameTypes;
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
public class GamePuzzleController extends GameController<GamePuzzleModel, GamePuzzleController, GamePuzzleState,
        GamePuzzleEvent,
        GameModel> {
    //    @Autowired
//    private ApplicationContext applicationContext;
    private StringBuilder logger = new StringBuilder();
    private Logger LOGGER = LoggerFactory.getLogger(GamePuzzleController.class);

    public void OnBet(GamePuzzleState from, GamePuzzleState to, GamePuzzleEvent event,
                      GameModel context) throws Exception {
        //下注
        BetPhaseData phaseData = (BetPhaseData) getPhaseData(GamePuzzleState.Bet.getValue());
        GamePuzzleModel gameModel = getGameModel();
        MoneyBetService moneyBetService = SpringIocUtil.getBean(MoneyBetService.class);
        TicketResult ticketResult = moneyBetService.betAndQueryPrizeLevel(this.getGameType(), gameModel.getUserName(),
                phaseData.getBetAmt(),
                gameModel
                        .getGameInstanceId());
        if (StringUtils.isEmpty(ticketResult.getTicketId())) {
            throw new BetErrorException("下注失败,请重试");
        }
        phaseData.setBetSerialNo(ticketResult.getTicketId());
        phaseData.setTicketResult(ticketResult);
        setPhaseSuccess(GamePuzzleState.Bet.getValue());

        logger.append("on bet");
    }

    public void OnDeal(GamePuzzleState from, GamePuzzleState to, GamePuzzleEvent event,
                       GameModel context) throws Exception {
        setPhaseSuccess(GamePuzzleState.Deal.getValue());
        logger.append("on bet");
    }

    public void OnGameOver(GamePuzzleState from, GamePuzzleState to, GamePuzzleEvent event,
                           GameModel context) throws Exception {
        //todo 调用兑奖接口

        setPhaseSuccess(GamePuzzleState.GameOver.getValue());
        logger.append("game over");
    }

    public void OnForceGameOver(GamePuzzleState from, GamePuzzleState to, GamePuzzleEvent event,
                                GameModel context) throws Exception {
        //todo 调用兑奖接口

        setPhaseSuccess(GamePuzzleState.GameOver.getValue());
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
    protected void afterTransitionCompleted(GamePuzzleState fromState, GamePuzzleState toState, GamePuzzleEvent
            event, GameModel context) throws Exception {

        GamePuzzleModel gameModel = getGameModel();
        GameManager bean = SpringIocUtil.getBean(GameManager.class);
        bean.saveGameModelToCacheAndAsyncDb(gameModel);
        if (toState == GamePuzzleState.GameOver) {
            GameManager.onGameOver(getGameModel().getUserName());
            bean.clearGameModelFromCache(gameModel.getUserName());
        }
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
