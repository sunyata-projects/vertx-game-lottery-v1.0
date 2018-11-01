package com.xt.landlords;

import com.xt.landlords.statemachine.GameController;
import org.sunyata.octopus.OctopusRequest;
import org.sunyata.octopus.OctopusResponse;

/**
 * Created by leo on 17/4/28.
 */
public class AbstractGameControllerCommandHandler extends AbstractAuthCommandHandler {


    @Override
    public boolean onExecuteBefore(OctopusRequest request, OctopusResponse response) {
        boolean flag = super.onExecuteBefore(request, response);
        return flag;
    }

    public boolean canAccpet(OctopusRequest request, Object obj) {
        String userName = request.getSession().getCurrentUser().getName();
        //是否可接受bet Event
        GameController gameController = GameManager.getGameController(userName);
        if (gameController != null) {
            boolean canAccept = gameController.canAccept(obj);
            return canAccept;
        }
        return false;
    }
}
