package com.xt.landlords.statemachine;

import com.xt.landlords.game.classic.GameClassicController;
import com.xt.landlords.game.classic.GameClassicEvent;
import com.xt.landlords.game.classic.GameClassicState;
import com.xt.landlords.game.crazy.GameCrazyController;
import com.xt.landlords.game.crazy.GameCrazyEvent;
import com.xt.landlords.game.crazy.GameCrazyState;
import com.xt.landlords.game.eliminate.GameEliminateController;
import com.xt.landlords.game.eliminate.GameEliminateEvent;
import com.xt.landlords.game.eliminate.GameEliminateState;
import com.xt.landlords.game.mission.GameMissionController;
import com.xt.landlords.game.mission.GameMissionEvent;
import com.xt.landlords.game.mission.GameMissionState;
import com.xt.landlords.game.puzzle.GamePuzzleController;
import com.xt.landlords.game.puzzle.GamePuzzleEvent;
import com.xt.landlords.game.puzzle.GamePuzzleState;
import com.xt.landlords.game.rank.GameRankController;
import com.xt.landlords.game.rank.GameRankEvent;
import com.xt.landlords.game.rank.GameRankState;
import com.xt.landlords.game.regular.GameRegularController;
import com.xt.landlords.game.regular.GameRegularEvent;
import com.xt.landlords.game.regular.GameRegularState;
import org.squirrelframework.foundation.fsm.StateMachineBuilder;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import org.sunyata.octopus.model.GameModel;

/**
 * Created by leo on 17/4/27.
 */
public class GameControllerFactory {

    static StateMachineBuilder<GameRegularController, GameRegularState, GameRegularEvent, GameModel> builder = null;

    static StateMachineBuilder<GamePuzzleController, GamePuzzleState, GamePuzzleEvent, GameModel> puzzleBuilder = null;

    static StateMachineBuilder<GameEliminateController, GameEliminateState, GameEliminateEvent, GameModel>
            eliminateBuilder = null;

    static StateMachineBuilder<GameCrazyController, GameCrazyState, GameCrazyEvent, GameModel> crazyBuilder = null;

    static StateMachineBuilder<GameMissionController, GameMissionState, GameMissionEvent, GameModel>
            missionBuilder = null;
    static StateMachineBuilder<GameRankController, GameRankState, GameRankEvent, GameModel> rankBuilder = null;

    static StateMachineBuilder<GameClassicController, GameClassicState, GameClassicEvent, GameModel> classicBuilder =
            null;


    public static synchronized StateMachineBuilder<GameClassicController, GameClassicState, GameClassicEvent, GameModel>
    getGameClassicBuilder() {

        if (classicBuilder == null) {
            classicBuilder = StateMachineBuilderFactory.create(GameClassicController.class, GameClassicState.class,
                    GameClassicEvent.class, GameModel.class, GameClassicController.class);
            classicBuilder.transitions().fromAmong(GameClassicState.Init, GameClassicState.Bet, GameClassicState.GuessSize).
                    to(GameClassicState.GameOver).on(GameClassicEvent.GameOver).callMethod("OnGameOver");

        }

        return classicBuilder;
    }

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

    public static synchronized StateMachineBuilder<GameMissionController, GameMissionState, GameMissionEvent, GameModel>
    getGameMissionBuilder() {

        if (missionBuilder == null) {
            missionBuilder = StateMachineBuilderFactory.create(GameMissionController.class, GameMissionState.class,
                    GameMissionEvent.class, GameModel.class, GameMissionController.class);
//            missionBuilder.transitions().fromAmong(GameMissionState.Init, GameMissionState.Bet).
//                    to(GameMissionState.GameOver).on(GameMissionEvent.GameOver).callMethod("OnGameOver");

        }

        return missionBuilder;
    }


    public static GameRegularController createGameRegularController(GameRegularState
                                                                            gameRegularState) {
        GameRegularController controller = getGameRegularBuilder().newStateMachine(gameRegularState);
//        controller.setContext(gameModel);
        return controller;
    }


    public static GameMissionController createGameMissionController(GameMissionState
                                                                            gameMissionState) {
        GameMissionController controller = getGameMissionBuilder().newStateMachine(gameMissionState);
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
        }

        return eliminateBuilder;
    }

    public static synchronized StateMachineBuilder<GameCrazyController, GameCrazyState, GameCrazyEvent, GameModel>
    getGameCrazyBuilder() {

        if (crazyBuilder == null) {
            crazyBuilder = StateMachineBuilderFactory.create(GameCrazyController.class, GameCrazyState.class,
                    GameCrazyEvent.class, GameModel.class, GameCrazyController.class);
        }

        return crazyBuilder;
    }

    public static GameController createGameCrazyController(GameCrazyState state) {
        GameCrazyController controller = getGameCrazyBuilder().newStateMachine(state);
        return controller;
    }

    public static GameController createGameRankController(GameRankState state) {
        GameRankController controller = getGameRankBuilder().newStateMachine(state);
        return controller;
    }

    public static synchronized StateMachineBuilder<GameRankController, GameRankState, GameRankEvent, GameModel>
    getGameRankBuilder() {
        if (rankBuilder == null) {
            rankBuilder = StateMachineBuilderFactory.create(GameRankController.class, GameRankState.class,
                    GameRankEvent.class, GameModel.class, GameRankController.class);
        }
        return rankBuilder;
    }


    public static GameClassicController createGameClassicController(GameClassicState gameClassicState) {
        GameClassicController controller = getGameClassicBuilder().newStateMachine(gameClassicState);
        return controller;
    }
}
