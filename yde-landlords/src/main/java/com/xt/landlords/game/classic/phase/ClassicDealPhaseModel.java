package com.xt.landlords.game.classic.phase;

import com.xt.landlords.game.crazy.phase.CrazyDealPhaseData;
import org.sunyata.octopus.model.GamePhaseModel;

/**
 * Created by leo on 17/5/18.
 */
public class ClassicDealPhaseModel extends GamePhaseModel<CrazyDealPhaseData> {
    public ClassicDealPhaseModel(String gameInstanceId, String phaseName, int orderBy) {
        super(gameInstanceId, phaseName, orderBy);
    }

    public ClassicDealPhaseModel() {

    }
}
