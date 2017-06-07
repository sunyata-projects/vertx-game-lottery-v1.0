package com.xt.landlords.game.eliminate.condition;

import com.xt.landlords.game.eliminate.GameEliminateModel;
import com.xt.landlords.game.eliminate.GameEliminateState;
import com.xt.landlords.game.eliminate.phase.EliminatePlayPhaseData;
import com.xt.landlords.game.eliminate.phase.EliminatePlayPhaseDataItem;
import com.xt.landlords.game.eliminate.phase.EliminatePlayPhaseModel;
import org.squirrelframework.foundation.fsm.AnonymousCondition;

/**
 * Created by leo on 17/5/26.
 */
public class BetCondition extends AnonymousCondition<GameEliminateModel> {
    @Override
    public boolean isSatisfied(GameEliminateModel gameEliminateModel) {
        EliminatePlayPhaseModel phase = (EliminatePlayPhaseModel) gameEliminateModel.getPhase(GameEliminateState.Play
                .getValue());
        if (phase == null) {
            return false;
        }
        EliminatePlayPhaseData phaseData = phase.getPhaseData();
        if (phaseData != null) {
            EliminatePlayPhaseDataItem lastPlayPhaseDataItem = phaseData.getLastPlayPhaseDataItem();
            if (lastPlayPhaseDataItem != null) {
                return lastPlayPhaseDataItem.getTotalDoubleKingCount() < 7;
            }
        }
        return false;

    }
}

