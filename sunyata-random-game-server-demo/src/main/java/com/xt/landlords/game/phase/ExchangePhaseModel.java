package com.xt.landlords.game.phase;

import org.sunyata.octopus.model.GamePhaseModel;

/**
 * Created by leo on 17/5/16.
 */
public class ExchangePhaseModel extends GamePhaseModel<ExchangePhaseData> {

    public ExchangePhaseModel() {

    }

    public ExchangePhaseModel(String gameInstanceId, String phaseName, int orderBy) {
        super(gameInstanceId, phaseName, orderBy);
    }

}
