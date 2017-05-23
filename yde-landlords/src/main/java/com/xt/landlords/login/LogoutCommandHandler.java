package com.xt.landlords.login;

import com.google.protobuf.InvalidProtocolBufferException;
import com.xt.landlords.AbstractAuthCommandHandler;
import com.xt.landlords.Commands;
import com.xt.landlords.event.LogoutEventMessage;
import org.springframework.stereotype.Component;
import org.sunyata.octopus.EventBus;
import org.sunyata.octopus.OctopusRequest;
import org.sunyata.octopus.OctopusResponse;

/**
 * Created by leo on 17/4/18.
 */
@Component(Commands.Logout)
public class LogoutCommandHandler extends AbstractAuthCommandHandler {
    @Override
    public void execute(OctopusRequest request, OctopusResponse response) throws InvalidProtocolBufferException, InvalidProtocolBufferException {
        //清除channel上下文中,当前登录的用户名称
        request.getContext().channel().attr(Login.KEY_USER_ID).set(null);
        //发布登录出事件
        EventBus.getLocalPubsubStore().publish(LogoutEventMessage.EventType, new LogoutEventMessage());
        response.writeAndFlush();
    }

    @Override
    public boolean isAsync() {
        return true;
    }
}
