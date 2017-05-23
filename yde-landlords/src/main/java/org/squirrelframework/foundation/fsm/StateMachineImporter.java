package org.squirrelframework.foundation.fsm;

import java.io.File;
import java.io.InputStream;

public interface StateMachineImporter<T extends org.squirrelframework.foundation.fsm.StateMachine<T, S, E, C>, S, E, C> {
    
    org.squirrelframework.foundation.fsm.StateMachineBuilder<T, S, E, C> importFromString(String content);
    
    org.squirrelframework.foundation.fsm.StateMachineBuilder<T, S, E, C> importFromFile(File content);
    
    StateMachineBuilder<T, S, E, C> importFromInputStream(InputStream content);
    
    void registerReusableInstance(Object instance);
    
    void registerReusableInstance(String instanceName, Object instance);
    
    void unregisterReusableInstance(String instanceName);
}
