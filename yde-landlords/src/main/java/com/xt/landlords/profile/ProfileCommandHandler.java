package com.xt.landlords.profile;

import com.google.protobuf.InvalidProtocolBufferException;
import com.xt.landlords.AbstractAuthCommandHandler;
import com.xt.landlords.Commands;
import com.xt.yde.protobuf.common.Common;
import com.xt.yde.thrift.login.LoginService;
import info.developerblog.spring.thrift.annotation.ThriftClient;
import org.springframework.stereotype.Component;
import org.sunyata.octopus.OctopusRequest;
import org.sunyata.octopus.OctopusResponse;

/**
 * Created by leo on 17/4/18.
 */
@Component(Commands.Profile)
public class ProfileCommandHandler extends AbstractAuthCommandHandler {

    @ThriftClient(serviceId = "login-service", path = "/api")
    LoginService.Client loginService;

    //public static final AttributeKey<String> KEY_USER_ID = AttributeKey.newInstance("USER_ID");

    @Override
    public void execute(OctopusRequest request, OctopusResponse response) throws Exception,
            InvalidProtocolBufferException {
        //LoginReq.LoginRequest.Builder builder = LoginReq.LoginRequest.newBuilder();
        //Common.ProfileRequestMsg loginRequest = Common.ProfileRequestMsg.parseFrom(request.getMessage().getBody());
        try {
            String name = request.getSession().getCurrentUser().getName();
            Common.ProfileResponseMsg lcl = Common.ProfileResponseMsg.newBuilder().setDisplayName("恭喜我中五百万")
                    .setMoney(1000).setUserId(name).setVc(1000).build();
            response.setBody(lcl.toByteArray());
            response.writeAndFlush();
        } catch (Exception ex) {
            response.setErrorCode(111);
        }

    }

    @Override
    public boolean isAsync() {
        return true;
    }
}
