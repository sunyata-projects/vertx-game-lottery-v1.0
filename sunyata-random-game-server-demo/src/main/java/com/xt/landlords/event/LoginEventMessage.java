package com.xt.landlords.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.sunyata.octopus.OctopusRequest;
import org.sunyata.octopus.store.pubsub.PubSubMessage;

/**
 * Created by leo on 17/4/19.
 */
@JsonIgnoreProperties(ignoreUnknown = true, value = {"request"})
public class LoginEventMessage extends PubSubMessage {

    public static final String EventType = "login";

    public OctopusRequest getRequest() {
        return request;
    }

    public LoginEventMessage setRequest(OctopusRequest request) {
        this.request = request;
        return this;
    }

    private OctopusRequest request;

    public LoginEventMessage(String userName) {
        this.userName = userName;
//        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public LoginEventMessage setUserName(String userName) {
        this.userName = userName;
        return this;
    }

//    public String getPassword() {
//        return password;
//    }
//
//    public LoginEventMessage setPassword(String password) {
//        this.password = password;
//        return this;
//    }

    private String userName;
//    private String password;
}
