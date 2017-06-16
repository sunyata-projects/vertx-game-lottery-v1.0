package com.xt.landlords.game.regular.phase;

import com.xt.landlords.game.regular.GameRegularState;
import org.sunyata.octopus.model.GamePhaseModel;

/**
 * Created by leo on 17/5/16.
 */
public class GuessSizePhaseModel extends GamePhaseModel<GuessSizePhaseData> {

    public GuessSizePhaseModel() {

    }

    public GuessSizePhaseModel(String gameInstanceId) {
        super(gameInstanceId, GameRegularState.GuessSize.getValue(),6);
    }

}
