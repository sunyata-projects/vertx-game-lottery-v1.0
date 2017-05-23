package org.squirrelframework.foundation.fsm.builder;

import org.squirrelframework.foundation.fsm.StateMachine;
import org.squirrelframework.foundation.fsm.builder.*;

public interface DeferBoundActionBuilder<T extends StateMachine<T, S, E, C>, S, E, C> {
    
    public org.squirrelframework.foundation.fsm.builder.DeferBoundActionFrom<T, S, E, C> fromAny();
    
    public org.squirrelframework.foundation.fsm.builder.DeferBoundActionFrom<T, S, E, C> from(S from);

}
