package org.squirrelframework.foundation.fsm.builder;

import org.squirrelframework.foundation.fsm.StateMachine;
import org.squirrelframework.foundation.fsm.builder.*;

public interface DeferBoundActionTo<T extends StateMachine<T, S, E, C>, S, E, C> {

    /**
     * Build transition event
     * @param event transition event
     * @return On clause builder
     */
    org.squirrelframework.foundation.fsm.builder.On<T, S, E, C> on(E event);
    
    org.squirrelframework.foundation.fsm.builder.On<T, S, E, C> onAny();
    
}
