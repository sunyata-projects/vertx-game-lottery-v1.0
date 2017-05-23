package org.sunyata.octopus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by leo on 17/4/18.
 */
public class MethodHandlerRouter {
    private List<HandlerInterceptor> inteceptors = new ArrayList<HandlerInterceptor>();
    private Executor executor = Executors.newCachedThreadPool();

    public void addInterceptors(List<HandlerInterceptor> interceptors) {
        this.inteceptors.addAll(interceptors);
    }

    public void route(OctopusRequest request, OctopusResponse response) throws Exception {
        CommandHandler handler = MethodHandlerLocatorFactory.getLocator().getMethodHandler(request);
        MethodHandlerTask task = new MethodHandlerTask(handler, inteceptors, request, response);
        if (handler.isAsync()) {
            executor.execute(task);
        } else {
            task.run();
        }
    }
}
