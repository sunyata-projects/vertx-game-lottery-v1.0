package org.squirrelframework.foundation.fsm.builder;

import org.squirrelframework.foundation.fsm.StateMachine;
import org.squirrelframework.foundation.fsm.builder.*;

/**
 * Created by kailianghe on 7/12/14.
 */
public interface MultiTo<T extends StateMachine<T, S, E, C>, S, E, C> {
    /**
     * Build transition event
     * @param events transition event
     * @return On clause builder
     */
    org.squirrelframework.foundation.fsm.builder.On<T, S, E, C> onEach(E... events);
}
