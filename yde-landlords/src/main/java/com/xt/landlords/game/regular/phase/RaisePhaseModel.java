package com.xt.landlords.game.regular.phase;

import org.sunyata.octopus.model.GamePhaseModel;

/**
 * Created by leo on 17/5/16.
 */
public class RaisePhaseModel extends GamePhaseModel<RaisePhaseData> {

    public RaisePhaseModel() {

    }

    public RaisePhaseModel(String gameInstanceId,String phaseName, int orderBy) {
        super(gameInstanceId,phaseName, orderBy);
    }

}
