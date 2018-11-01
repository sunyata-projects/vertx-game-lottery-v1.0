package org.squirrelframework.foundation.fsm;

import org.squirrelframework.foundation.component.SquirrelProvider;
import org.squirrelframework.foundation.util.TypeReference;

import java.io.File;
import java.io.InputStream;

public class UntypedStateMachineImporter {
    
    private final StateMachineImporter<org.squirrelframework.foundation.fsm.UntypedStateMachine, Object, Object, Object> delegator =
            SquirrelProvider.getInstance().newInstance(
                    new TypeReference<StateMachineImporter<org.squirrelframework.foundation.fsm.UntypedStateMachine, Object, Object, Object>>() {
            });
    
    public UntypedStateMachineBuilder importDefinition(Object content) {
        final StateMachineBuilder<UntypedStateMachine, Object, Object, Object> stateMachineBuilder;
        if(content instanceof String) {
            stateMachineBuilder = delegator.importFromString((String)content);
        } else if(content instanceof File) {
            stateMachineBuilder = delegator.importFromFile((File)content);
        } else if(content instanceof InputStream) {
            stateMachineBuilder = delegator.importFromInputStream((InputStream)content);
        } else {
            throw new IllegalArgumentException("Cannot support import from "+content.getClass().getName()+".");
        }
        return StateMachineBuilderFactory.create(stateMachineBuilder);
    }
    
    public void registerReusableInstance(Object instance) {
        delegator.registerReusableInstance(instance);
    }

    public void unregisterReusableInstance(String instanceName) {
        delegator.unregisterReusableInstance(instanceName);
    }

    public void registerReusableInstance(String instanceName, Object instance) {
        delegator.registerReusableInstance(instanceName, instance);
    }
    
}
