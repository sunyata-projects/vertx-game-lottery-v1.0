package com.xt.landlords.game.rank.condition;

import com.xt.landlords.game.mission.GameMissionModel;
import com.xt.landlords.game.mission.phase.MissionPlayPhaseData;
import com.xt.landlords.game.mission.phase.MissionPlayPhaseModel;
import org.squirrelframework.foundation.fsm.AnonymousCondition;

/**
 * Created by leo on 17/4/26.
 */
public class PlayingCondition extends AnonymousCondition<GameMissionModel> {


    @Override
    public boolean isSatisfied(GameMissionModel gameModel) {
        MissionPlayPhaseModel phaseModel = (MissionPlayPhaseModel) gameModel.getLastPlayPhaseModel();
        if (phaseModel == null) {
            return true;
        }
        MissionPlayPhaseData phaseData = phaseModel.getPhaseData();
        return phaseData == null || !phaseData.isIfEnd();
    }
}
