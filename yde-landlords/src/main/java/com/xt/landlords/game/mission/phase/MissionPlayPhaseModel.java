package com.xt.landlords.game.mission.phase;

import org.sunyata.octopus.model.GamePhaseModel;

/**
 * Created by leo on 17/5/16.
 */
public class MissionPlayPhaseModel extends GamePhaseModel<MissionPlayPhaseData> {

    private int missionIndex;

    public MissionPlayPhaseModel() {

    }

    public MissionPlayPhaseModel(String gameInstanceId, String phaseName, int orderBy) {
        super(gameInstanceId, phaseName, orderBy);
    }

    public void setMissionIndex(int missionIndex) {
        this.missionIndex = missionIndex;
    }

    public int getMissionIndex() {
        return missionIndex;
    }


}
