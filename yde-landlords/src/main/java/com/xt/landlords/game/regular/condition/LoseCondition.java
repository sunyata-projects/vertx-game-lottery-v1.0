package com.xt.landlords.game.regular.condition;

import com.xt.landlords.game.regular.GameRegularModel;
import com.xt.landlords.game.regular.GameRegularState;
import com.xt.landlords.game.regular.phase.RegularPlayPhaseData;
import com.xt.landlords.game.regular.phase.RegularPlayPhaseModel;
import org.squirrelframework.foundation.fsm.AnonymousCondition;

/**
 * Created by leo on 17/4/26.
 */
public class LoseCondition extends AnonymousCondition<GameRegularModel> {


    @Override
    public boolean isSatisfied(GameRegularModel gameModel) {
        RegularPlayPhaseModel phaseModel = (RegularPlayPhaseModel) gameModel.getPhase(GameRegularState.Playing
                .getValue());
        if (phaseModel == null) {
            return false;
        }
        RegularPlayPhaseData phaseData = phaseModel.getPhaseData();
        return phaseData != null && phaseData.isIfEnd() && !phaseData.isWin();
    }
}
