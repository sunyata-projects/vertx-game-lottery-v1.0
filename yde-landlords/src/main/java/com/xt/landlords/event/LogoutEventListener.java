package com.xt.landlords.event;

import com.xt.landlords.GameManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.octopus.store.StoreFactory;
import org.sunyata.octopus.store.pubsub.PubSubListener;

/**
 * Created by leo on 17/4/19.
 */
@Component
public class LogoutEventListener implements PubSubListener<LoginEventMessage> {

    @Autowired
    StoreFactory storeFactory;

    @Override
    public void onMessage(LoginEventMessage data) {
        data.getRequest().getSession().clear();//清除Session
        GameManager.onUserLeft(data.getUserName());//移出游戏
//        Store store = storeFactory.createStore(UUID.fromString(data.getUserName()));
//        RedisSession session = RedisSession.newInstance(data.getUserName(), store, data.getRequest().getContext());
//        long now = System.currentTimeMillis();
//        session.setCreationTime(now);
//        session.setLastAccessedTime(now);
//        session.setUser(new User(data.getUserName(), data.getPassword()));
//        data.getRequest().setSession(session);
    }
}
