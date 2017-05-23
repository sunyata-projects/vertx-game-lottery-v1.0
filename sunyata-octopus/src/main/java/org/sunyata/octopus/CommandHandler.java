package org.sunyata.octopus;

/**
 * Created by leo on 17/4/18.
 */
public interface CommandHandler {

    boolean onExecuteBefore(OctopusRequest request, OctopusResponse response);

    void execute(OctopusRequest request, OctopusResponse response) throws Exception;

    void run(OctopusRequest request, OctopusResponse response) throws Exception;

    boolean isAsync();
}
