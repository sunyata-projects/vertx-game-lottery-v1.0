package com.xt.landlords.game.crazy.phase;

import org.sunyata.octopus.model.GamePhaseModel;

/**
 * Created by leo on 17/5/16.
 */
public class CrazyClearPhaseModel extends GamePhaseModel<CrazyClearPhaseData> {

    public CrazyClearPhaseModel() {

    }

    public CrazyClearPhaseModel(String gameInstanceId, String phaseName, int orderBy) {
        super(gameInstanceId,phaseName, orderBy);
    }

}
