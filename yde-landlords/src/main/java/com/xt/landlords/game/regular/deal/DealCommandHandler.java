package com.xt.landlords.game.regular.deal;

import com.xt.landlords.AbstractGameControllerCommandHandler;
import com.xt.landlords.Commands;
import org.springframework.stereotype.Component;
import org.sunyata.octopus.OctopusRequest;
import org.sunyata.octopus.OctopusResponse;

/**
 * Created by leo on 17/5/15.
 */
@Component(Commands.RegularDeal)
public class DealCommandHandler extends AbstractGameControllerCommandHandler {
    class CommandErrorCode {

    }

    @Override
    public void execute(OctopusRequest request, OctopusResponse response) throws Exception {
        try {


        } catch (Exception ex) {

        } finally {
            response.writeAndFlush();
        }
    }

    @Override
    public boolean isAsync() {
        return false;
    }
}

