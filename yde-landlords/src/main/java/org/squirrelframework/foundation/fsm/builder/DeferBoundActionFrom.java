package org.squirrelframework.foundation.fsm.builder;

import org.squirrelframework.foundation.fsm.StateMachine;
import org.squirrelframework.foundation.fsm.builder.*;

public interface DeferBoundActionFrom<T extends StateMachine<T, S, E, C>, S, E, C> {
    
    /**
     * Build transition target state and return to clause builder
     * @param stateId id of state
     * @return To clause builder
     */
    org.squirrelframework.foundation.fsm.builder.DeferBoundActionTo<T, S, E, C> to(S stateId);
    
    org.squirrelframework.foundation.fsm.builder.DeferBoundActionTo<T, S, E, C> toAny();
    
}
