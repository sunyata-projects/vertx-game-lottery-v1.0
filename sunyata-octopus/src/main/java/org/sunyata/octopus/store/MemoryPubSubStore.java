/**
 * Copyright 2012 Nikita Koksharov
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sunyata.octopus.store;


import org.sunyata.octopus.store.pubsub.PubSubListener;
import org.sunyata.octopus.store.pubsub.PubSubMessage;
import org.sunyata.octopus.store.pubsub.PubSubStore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class MemoryPubSubStore implements PubSubStore {

    ConcurrentMap<String, List<PubSubListener>> map = new ConcurrentHashMap<>();

    AtomicBoolean running = new AtomicBoolean(true);

    @Override
    public void publish(String type, PubSubMessage msg) {
        if (running.get()) {
            List<PubSubListener> orDefault = map.getOrDefault(type, null);
            if (orDefault != null) {
                orDefault.stream().forEach(p -> p.onMessage(msg));
            }
        }
    }

    @Override
    public <T extends PubSubMessage> void subscribe(String type, PubSubListener<T> listener, Class<T> clazz) {
        if (running.get()) {
            List<PubSubListener> orDefault = map.getOrDefault(type, null);
            if (orDefault != null) {
                orDefault.add(listener);
            } else {
                List<PubSubListener> objects = new ArrayList<>();
                objects.add(listener);
                map.put(type, objects);
            }
        }
    }

    @Override
    public void unsubscribe(String type) {
        map.remove(type);
    }

    @Override
    public void shutdown() {
        running.getAndSet(false);
    }

}
