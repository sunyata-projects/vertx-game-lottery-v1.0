package org.squirrelframework.foundation.fsm;

import java.util.List;

public interface MutableTransition<T extends org.squirrelframework.foundation.fsm.StateMachine<T, S, E, C>, S, E, C> extends ImmutableTransition<T, S, E, C> {
    
    void setSourceState(org.squirrelframework.foundation.fsm.ImmutableState<T, S, E, C> state);
    
    void setTargetState(ImmutableState<T, S, E, C> state);
    
    void addAction(org.squirrelframework.foundation.fsm.Action<T, S, E, C> newAction);
    
    void addActions(List<? extends Action<T, S, E, C>> newActions);
    
    void setCondition(Condition<C> condition);
    
    void setEvent(E event);
    
    void setType(TransitionType type);
    
    void setPriority(int priority);
}
