package com.xt.landlords.game.rank.phase;

import org.sunyata.octopus.model.GamePhaseModel;

/**
 * Created by leo on 17/5/16.
 */
public class RankTurnPhaseModel extends GamePhaseModel<RankTurnPhaseData> {

    public RankTurnPhaseModel(String gameInstanceId, String phaseName, int orderBy) {
        super(gameInstanceId, phaseName, orderBy);
    }

    public RankTurnPhaseModel() {

    }
}
