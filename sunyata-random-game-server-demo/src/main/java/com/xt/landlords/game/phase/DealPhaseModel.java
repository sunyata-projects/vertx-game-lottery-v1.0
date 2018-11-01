package com.xt.landlords.game.phase;

import org.sunyata.octopus.model.GamePhaseModel;

/**
 * Created by leo on 17/5/18.
 */
public class DealPhaseModel extends GamePhaseModel<DealPhaseData> {
    public DealPhaseModel(String gameInstanceId, String phaseName, int orderBy) {
        super(gameInstanceId, phaseName, orderBy);
    }

    public DealPhaseModel() {

    }
}
