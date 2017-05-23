package org.sunyata.octopus;

import org.sunyata.octopus.store.StoreFactory;

/**
 * Created by leo on 17/4/19.
 */
public class OctopusConfiguration {
    private int port;

    public StoreFactory getStoreFactory() {
        return storeFactory;
    }

    public OctopusConfiguration setStoreFactory(StoreFactory storeFactory) {
        this.storeFactory = storeFactory;
        return this;
    }

    private StoreFactory storeFactory;

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }


    public <T extends MethodHandlerLocator> void setMethodHandlerLocator(Class<T> methodHandlerLocator) {
        MethodHandlerLocatorFactory.setMethodHandlerLocator(methodHandlerLocator);
    }

}
