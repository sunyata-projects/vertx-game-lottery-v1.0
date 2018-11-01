package com.xt.landlords;

import com.xt.landlords.login.Login;
import org.sunyata.octopus.OctopusRequest;
import org.sunyata.octopus.OctopusResponse;
import org.sunyata.octopus.Session;

/**
 * Created by leo on 17/4/28.
 */
public class AbstractAuthCommandHandler extends AbstractCommandHandler {


    @Override
    public boolean onExecuteBefore(OctopusRequest request, OctopusResponse response) {
        Session session = request.getSession();
        String s = request.getContext().channel().attr(Login.KEY_USER_ID).get();
        if (null == session || null == s) {//没有登录
            //构造登录指令发送到客户端
            response.setErrorCode(CommonCommandErrorCode.NotLoginException);
            return false;
        }
        return true;
    }
}
