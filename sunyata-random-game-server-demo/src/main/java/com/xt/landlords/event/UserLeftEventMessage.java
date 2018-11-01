package com.xt.landlords.event;

import org.sunyata.octopus.store.pubsub.PubSubMessage;

public class UserLeftEventMessage extends PubSubMessage {

    public static final String EventType = "userLeft";
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
