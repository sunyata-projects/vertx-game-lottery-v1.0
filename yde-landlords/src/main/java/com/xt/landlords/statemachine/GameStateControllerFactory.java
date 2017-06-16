package com.xt.landlords.statemachine;

import com.xt.landlords.game.eliminate.GameEliminateController;
import com.xt.landlords.game.eliminate.GameEliminateEvent;
import com.xt.landlords.game.eliminate.GameEliminateState;
import com.xt.landlords.game.puzzle.GamePuzzleController;
import com.xt.landlords.game.puzzle.GamePuzzleEvent;
import com.xt.landlords.game.puzzle.GamePuzzleState;
import com.xt.landlords.game.regular.GameRegularController;
import com.xt.landlords.game.regular.GameRegularEvent;
import com.xt.landlords.game.regular.GameRegularState;
import org.squirrelframework.foundation.fsm.StateMachineBuilder;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import org.sunyata.octopus.model.GameModel;

/**
 * Created by leo on 17/4/27.
 */
public class GameStateControllerFactory {

    static StateMachineBuilder<GameRegularController, GameRegularState, GameRegularEvent, GameModel> builder = null;
    static StateMachineBuilder<GamePuzzleController, GamePuzzleState, GamePuzzleEvent, GameModel> puzzleBuilder = null;

    static StateMachineBuilder<GameEliminateController, GameEliminateState, GameEliminateEvent, GameModel>
            eliminateBuilder = null;

    public static synchronized StateMachineBuilder<GameRegularController, GameRegularState, GameRegularEvent, GameModel>
    getGameRegularBuilder() {

        if (builder == null) {
            builder = StateMachineBuilderFactory.create(GameRegularController.class, GameRegularState.class,
                    GameRegularEvent.class, GameModel.class, GameRegularController.class);
            builder.transitions().fromAmong(GameRegularState.Init, GameRegularState.Bet, GameRegularState.GuessSize).
                    to(GameRegularState.GameOver).on(GameRegularEvent.GameOver).callMethod("OnGameOver");

        }

        return builder;
    }

    public static GameRegularController createGameRegularController(GameRegularState
                                                                            gameRegularState) {
        GameRegularController controller = getGameRegularBuilder().newStateMachine(gameRegularState);
//        controller.setContext(gameModel);
        return controller;
    }

    public static GameController createGamePuzzleController(GamePuzzleState state) {
        GamePuzzleController controller = getGamePuzzleBuilder().newStateMachine(state);
//        controller.setContext(gameModel);
        return controller;
    }

    public static GameController createGameEliminateController(GameEliminateState state) {
        GameEliminateController controller = getGameEliminateBuilder().newStateMachine(state);
        return controller;
    }

    public static synchronized StateMachineBuilder<GamePuzzleController, GamePuzzleState, GamePuzzleEvent, GameModel>
    getGamePuzzleBuilder() {

        if (puzzleBuilder == null) {
            puzzleBuilder = StateMachineBuilderFactory.create(GamePuzzleController.class, GamePuzzleState.class,
                    GamePuzzleEvent.class, GameModel.class, GamePuzzleController.class);
            puzzleBuilder.transitions().fromAmong(GamePuzzleState.Deal).to(GamePuzzleState.GameOver).on
                    (GamePuzzleEvent.GameOver).callMethod("OnGameOver");
//            puzzleBuilder.transitions().fromAmong(GamePuzzleState.Init, GamePuzzleState.Bet).to(GamePuzzleState
//                    .GameOver).on(GamePuzzleEvent.GameOver).callMethod("OnForceGameOver");

        }

        return puzzleBuilder;
    }


    public static synchronized StateMachineBuilder<GameEliminateController, GameEliminateState, GameEliminateEvent,
            GameModel>
    getGameEliminateBuilder() {

        if (eliminateBuilder == null) {
            eliminateBuilder = StateMachineBuilderFactory.create(GameEliminateController.class, GameEliminateState
                            .class,
                    GameEliminateEvent.class, GameModel.class, GameEliminateController.class);
//            eliminateBuilder.transitions().fromAmong(GameEliminateState.Bet, GameEliminateState.Deal).to
//                    (GameEliminateState.GameOver).on(GameEliminateEvent.ForceGameOver).callMethod("OnForceGameOver");
//            eliminateBuilder.transitions().fromAmong(GameEliminateState.Play).to(GameEliminateState.GameOver).on
//                    (GameEliminateEvent.GameOver).callMethod("OnGameOver");
//            puzzleBuilder.transitions().fromAmong(GamePuzzleState.Init, GamePuzzleState.Bet).to(GamePuzzleState
//                    .GameOver).on(GamePuzzleEvent.GameOver).callMethod("OnForceGameOver");

        }

        return eliminateBuilder;
    }
}
