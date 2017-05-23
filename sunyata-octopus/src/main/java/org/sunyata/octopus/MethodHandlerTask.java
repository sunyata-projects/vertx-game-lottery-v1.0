package org.sunyata.octopus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 17/4/18.
 */
public class MethodHandlerTask implements Runnable {
    ;

    private CommandHandler handler;
    private List<HandlerInterceptor> inteceptors;
    private OctopusRequest request;
    private OctopusResponse response;


    public MethodHandlerTask(CommandHandler handler, List<HandlerInterceptor> inteceptors, OctopusRequest requestWrapper,
                             OctopusResponse responseWrapper) {
        this.handler = handler;
        this.inteceptors = inteceptors;
        this.request = requestWrapper;
        this.response = responseWrapper;
        if (this.inteceptors == null) {
            this.inteceptors = new ArrayList<HandlerInterceptor>();
        }
    }

    @Override
    public void run() {

//        if (handler == null) {
//            response.setStatus(Status.Notfound.value());
//            response.setErr("invoke method not found");
//            response.getServerHandler().write(response);
//            return;
//        }

        for (HandlerInterceptor interceptor : inteceptors) {
            try {
                boolean flag = interceptor.preHandle(request, response, handler);
                if (!flag) return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            handler.run(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (HandlerInterceptor interceptor : inteceptors) {
            try {
                interceptor.postHandle(request, response, handler);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


//        if (handler.isReply()) {
//            response.writeAndFlush();
//        }

        for (HandlerInterceptor interceptor : inteceptors) {
            try {
                interceptor.afterCompletion(request, response, handler);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}