package com.xt.landlords.statemachine;

import org.squirrelframework.foundation.fsm.StateMachine;
import org.squirrelframework.foundation.fsm.impl.AbstractStateMachine;

/**
 * Created by leo on 17/4/27.
 */
//@Configurable(preConstruction = true)
public abstract class GameController<T extends StateMachine<T, S, E, C>, S, E, C> extends AbstractStateMachine<T, S, E, C> {
//    @Autowired
//    private ApplicationContext applicationContext;

    public C getGameModel() {
        return gameModel;
    }

    public GameController setGameModel(C gameModel) {
        this.gameModel = gameModel;
        return this;
    }

    private C gameModel;

    @Override
    protected void beforeActionInvoked(S fromState, S toState, E event, C context) {
        super.beforeActionInvoked(fromState, toState, event, context);
        gameModel = context;
    }

    public abstract E getBetEvent();
    public abstract S getInitState();

    public abstract Integer getGameType();


    public boolean canAcceptBetEvent() {
        return super.canAccept(getBetEvent());
    }
}
