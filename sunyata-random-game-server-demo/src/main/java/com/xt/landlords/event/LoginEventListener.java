package com.xt.landlords.event;

import org.springframework.stereotype.Component;
import org.sunyata.octopus.store.pubsub.PubSubListener;

/**
 * Created by leo on 17/4/19.
 */
@Component
public class LoginEventListener implements PubSubListener<LoginEventMessage> {
    @Override
    public void onMessage(LoginEventMessage data) {
    }
}
