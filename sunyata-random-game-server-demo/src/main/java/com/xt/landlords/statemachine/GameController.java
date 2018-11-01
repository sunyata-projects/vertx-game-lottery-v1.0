package com.xt.landlords.statemachine;

import org.squirrelframework.foundation.fsm.StateMachine;
import org.squirrelframework.foundation.fsm.impl.AbstractStateMachine;
import org.sunyata.octopus.model.GameModel;
import org.sunyata.octopus.model.GamePhaseModel;
import org.sunyata.octopus.model.PhaseData;
import org.sunyata.octopus.model.PhaseState;

/**
 * Created by leo on 17/4/27.
 */
//@Configurable(preConstruction = true)
public abstract class GameController<G extends GameModel, T extends StateMachine<T, S, E, C>, S, E, C> extends
        AbstractStateMachine<T, S,
                E, C> {
//    @Autowired
//    private ApplicationContext applicationContext;

    //private C context;
    private G gameModel;

    /*public C getContext() {
        return context;
    }

    public GameController setContext(C gameModel) {
        this.context = gameModel;
        return this;
    }*/


    @Override
    protected void beforeActionInvoked(S fromState, S toState, E event, C context) {
        super.beforeActionInvoked(fromState, toState, event, context);
        //this.context = context;
    }

    @Override
    protected void afterActionInvoked(S fromState, S toState, E event, C context) {
        super.afterActionInvoked(fromState, toState, event, context);
    }

    @Override
    protected void afterTransitionCompleted(S fromState, S toState, E event, C context) throws Exception {
        super.afterTransitionCompleted(fromState, toState, event, context);
    }

    public E getFirstEvent() {
        return (E) gameModel.getFirstEvent();
    }

    public S getInitState() {
        return (S) gameModel.getInitState();
    }

    public abstract Integer getGameType();


    public boolean canAcceptBetEvent() {
        return super.canAccept(getFirstEvent());
    }

    public void loadFromGameModel(G model) {
    }

    public GameController setGameModel(G gameModel) {
        this.gameModel = gameModel;
        return this;
    }


    public G getGameModel() {
        return gameModel;
    }

    public PhaseData getPhaseData(String phaseName) {
        GamePhaseModel phase = getPhase(phaseName);
        return phase != null ? phase.getPhaseData() : null;
    }

    public GamePhaseModel getPhase(String phaseName) {
        GamePhaseModel gamePhaseModel = this.getGameModel().getPhases().stream().filter(p -> p.getPhaseName()
                .equalsIgnoreCase(phaseName))
                .findFirst().orElse(null);
        return gamePhaseModel;
    }

    public void setPhaseSuccess(String phaseName) {
        getPhase(phaseName).setPhaseState(PhaseState.Success);
    }
}
