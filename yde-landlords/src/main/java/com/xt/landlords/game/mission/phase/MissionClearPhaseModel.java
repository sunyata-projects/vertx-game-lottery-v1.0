package com.xt.landlords.game.mission.phase;

import org.sunyata.octopus.model.GamePhaseModel;

/**
 * Created by leo on 17/5/16.
 */
public class MissionClearPhaseModel extends GamePhaseModel<MissionClearPhaseData> {

    public MissionClearPhaseModel () {

    }

    public MissionClearPhaseModel (String gameInstanceId, String phaseName, int orderBy) {
        super(gameInstanceId,phaseName, orderBy);
    }

}
