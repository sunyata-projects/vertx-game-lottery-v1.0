package org.sunyata.octopus;

import org.sunyata.octopus.store.MemoryStoreFactory;
import org.sunyata.octopus.store.StoreFactory;
import org.sunyata.octopus.store.pubsub.PubSubStore;

/**
 * Created by leo on 17/4/19.
 */
public class EventBus {

    private static StoreFactory memoryStoreFactory;
    private static StoreFactory distributeStoreFactory;


    public static PubSubStore getLocalPubsubStore() {
        if (memoryStoreFactory == null) {
            EventBus.memoryStoreFactory = new MemoryStoreFactory();
        }
        return memoryStoreFactory.pubSubStore();
    }

    public static PubSubStore getDistributePubsubStore() throws Exception {
        if (EventBus.distributeStoreFactory == null) {
            throw new Exception("storyFactory is null,must set a value");
        }
        return distributeStoreFactory.pubSubStore();
    }

    public static void setDistributeStoreFactory(StoreFactory distributeStoreFactory) {
        EventBus.distributeStoreFactory = distributeStoreFactory;
    }
}
