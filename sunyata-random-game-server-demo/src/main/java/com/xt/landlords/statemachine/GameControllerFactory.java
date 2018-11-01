package com.xt.landlords.statemachine;

import com.xt.landlords.game.classic.GameClassicController;
import com.xt.landlords.game.classic.GameClassicEvent;
import com.xt.landlords.game.classic.GameClassicState;
import org.squirrelframework.foundation.fsm.StateMachineBuilder;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import org.sunyata.octopus.model.GameModel;

/**
 * Created by leo on 17/4/27.
 */
public class GameControllerFactory {

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


    public static GameClassicController createGameClassicController(GameClassicState gameClassicState) {
        GameClassicController controller = getGameClassicBuilder().newStateMachine(gameClassicState);
        return controller;
    }
}
