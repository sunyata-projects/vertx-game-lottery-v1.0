package org.sunyata.octopus;

/**
 * Created by leo on 17/4/18.
 */
public interface HandlerInterceptor {

    boolean preHandle(OctopusRequest request, OctopusResponse response, CommandHandler handler)
            throws Exception;

    void postHandle(OctopusRequest request, OctopusResponse response, CommandHandler handler)
            throws Exception;

    void afterCompletion(OctopusRequest request, OctopusResponse response, CommandHandler handler)
            throws Exception;


}
