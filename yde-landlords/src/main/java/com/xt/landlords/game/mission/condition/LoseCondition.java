package com.xt.landlords.game.mission.condition;

import com.xt.landlords.game.mission.GameMissionModel;
import com.xt.landlords.game.mission.phase.MissionPlayPhaseData;
import com.xt.landlords.game.mission.phase.MissionPlayPhaseModel;
import org.squirrelframework.foundation.fsm.AnonymousCondition;

/**
 * Created by leo on 17/4/26.
 */
public class LoseCondition extends AnonymousCondition<GameMissionModel> {


    @Override
    public boolean isSatisfied(GameMissionModel gameModel) {
        MissionPlayPhaseModel phaseModel = (MissionPlayPhaseModel) gameModel.getLastPlayPhaseModel();
        if (phaseModel == null) {
            return false;
        }
        MissionPlayPhaseData phaseData = phaseModel.getPhaseData();
        return phaseData != null && phaseData.isIfEnd() && !phaseData.isWin();
    }
}
