package com.xt.landlords;

import com.xt.landlords.login.Login;
import org.apache.commons.lang.StringUtils;
import org.sunyata.octopus.*;

/**
 * Created by leo on 17/4/28.
 */
public class AbstractCommandHandler implements CommandHandler {
    @Override
    public boolean onExecuteBefore(OctopusRequest request, OctopusResponse response) {
        return false;
    }

    @Override
    public void execute(OctopusRequest request, OctopusResponse response) throws Exception {

    }

    @Override
    public final void run(OctopusRequest request, OctopusResponse response) throws Exception {
        String userName = request.getContext().channel().attr(Login.KEY_USER_ID).get();
        if (!StringUtils.isEmpty(userName)) {
            Session session = SessionManager.getSession(userName);
            request.setSession(session);
        }
        if (onExecuteBefore(request, response)) {
            execute(request, response);
        } else {
            response.writeAndFlush();
        }
    }


    @Override
    public boolean isAsync() {
        return false;
    }
}
