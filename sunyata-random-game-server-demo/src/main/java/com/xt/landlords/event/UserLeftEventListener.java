package com.xt.landlords.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.octopus.SessionManager;
import org.sunyata.octopus.store.StoreFactory;
import org.sunyata.octopus.store.pubsub.PubSubListener;

/**
 * Created by leo on 17/4/19.
 */
@Component
public class UserLeftEventListener implements PubSubListener<UserLeftEventMessage> {

    @Autowired
    StoreFactory storeFactory;


    @Override
    public void onMessage(UserLeftEventMessage data) {
//        Session session = SessionManager.getSession(data.getUserName());
//        session.clear();
        SessionManager.removeFromLocal(data.getUserName());
    }
}
