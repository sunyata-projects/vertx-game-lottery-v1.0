package org.sunyata.octopus;

/**
 * Created by leo on 17/4/18.
 */
public interface Server {
    void start() throws InterruptedException;

    void stop();

    void shutdown();

    void service(OctopusRequest requestWrapper, OctopusResponse responseWrapper) throws Exception;

    OctopusConfiguration getConfiguration();
}
