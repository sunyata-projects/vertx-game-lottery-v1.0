package org.sunyata.octopus;

/**
 * Created by leo on 17/4/18.
 */
public interface MethodHandlerLocator {
    CommandHandler getMethodHandler(OctopusRequest request);
}
