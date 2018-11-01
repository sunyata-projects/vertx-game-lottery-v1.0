package org.squirrelframework.foundation.fsm;

public interface UntypedStateMachineBuilder extends StateMachineBuilder<org.squirrelframework.foundation.fsm.UntypedStateMachine, Object, Object, Object> {
    
    <T extends org.squirrelframework.foundation.fsm.UntypedStateMachine> T newUntypedStateMachine(Object initialStateId);
    
    <T extends org.squirrelframework.foundation.fsm.UntypedStateMachine> T newUntypedStateMachine(Object initialStateId, Object... extraParams);
    
    <T extends UntypedStateMachine> T newUntypedStateMachine(
            Object initialStateId, org.squirrelframework.foundation.fsm.StateMachineConfiguration configuration,
            Object... extraParams);
    
    <T> T newAnyStateMachine(Object initialStateId);
            
    <T> T newAnyStateMachine(Object initialStateId, Object... extraParams);
    
    <T> T newAnyStateMachine(Object initialStateId, StateMachineConfiguration configuration, Object... extraParams);
}
