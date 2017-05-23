package org.squirrelframework.foundation.fsm;

public interface StateMachineDataSerializer<T extends org.squirrelframework.foundation.fsm.StateMachine<T, S, E, C>, S, E, C> {
    
    String serialize(org.squirrelframework.foundation.fsm.StateMachineData.Reader<T, S, E, C> data);
    
    StateMachineData.Reader<T, S, E, C> deserialize(String value);
}
