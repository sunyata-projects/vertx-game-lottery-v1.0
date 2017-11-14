package com.xt.landlords.game.classic.phase;

import org.sunyata.octopus.model.GamePhaseModel;

/**
 * Created by leo on 17/5/16.
 */
public class ClassicClearPhaseModel extends GamePhaseModel<ClassicClearPhaseData> {

    public ClassicClearPhaseModel() {

    }

    public ClassicClearPhaseModel(String gameInstanceId, String phaseName, int orderBy) {
        super(gameInstanceId,phaseName, orderBy);
    }

}
