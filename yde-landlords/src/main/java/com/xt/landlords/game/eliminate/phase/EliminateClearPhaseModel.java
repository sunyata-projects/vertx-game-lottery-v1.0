package com.xt.landlords.game.eliminate.phase;

import org.sunyata.octopus.model.GamePhaseModel;

/**
 * Created by leo on 17/5/16.
 */
public class EliminateClearPhaseModel extends GamePhaseModel<EliminateClearPhaseData> {

    public EliminateClearPhaseModel() {

    }

    public EliminateClearPhaseModel(String gameInstanceId, String phaseName, int orderBy) {
        super(gameInstanceId,phaseName, orderBy);
    }

}
