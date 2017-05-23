package com.xt.landlords.event;

import org.sunyata.octopus.store.pubsub.PubSubMessage;

/**
 * Created by leo on 17/4/19.
 */
public class LogoutEventMessage extends PubSubMessage {

    public static final String EventType = "logout";
}
