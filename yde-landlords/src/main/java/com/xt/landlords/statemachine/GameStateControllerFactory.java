package com.xt.landlords.statemachine;

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

    public static synchronized StateMachineBuilder<GameRegularController, GameRegularState, GameRegularEvent, GameModel>
    getGameRegularBuilder() {

        if (builder == null) {
            builder = StateMachineBuilderFactory.create(GameRegularController.class, GameRegularState.class,
                    GameRegularEvent.class, GameModel.class, GameRegularController.class);
            builder.transitions().fromAmong(GameRegularState.Init, GameRegularState.Bet).
                    to(GameRegularState.GameOver).on(GameRegularEvent.GameOver).callMethod("OnGameOver");

        }

        return builder;
    }

    public static GameRegularController createGameRegularController(GameRegularState
                                                                            gameRegularState) {
        GameRegularController controller = getGameRegularBuilder().newStateMachine(gameRegularState);
//        controller.setGameModel(gameModel);
        return controller;
    }

    public static GameController createGamePuzzleController(GamePuzzleState state) {
        GamePuzzleController controller = getGamePuzzleBuilder().newStateMachine(state);
//        controller.setGameModel(gameModel);
        return controller;
    }

    public static synchronized StateMachineBuilder<GamePuzzleController, GamePuzzleState, GamePuzzleEvent, GameModel>
    getGamePuzzleBuilder() {

        if (puzzleBuilder == null) {
            puzzleBuilder = StateMachineBuilderFactory.create(GamePuzzleController.class, GamePuzzleState.class,
                    GamePuzzleEvent.class, GameModel.class, GamePuzzleController.class);
            puzzleBuilder.transitions().fromAmong(GamePuzzleState.Init, GamePuzzleState.Bet, GamePuzzleState.Deal).
                    to(GamePuzzleState.GameOver).on(GamePuzzleEvent.GameOver).callMethod("OnGameOver");

        }

        return puzzleBuilder;
    }
}
