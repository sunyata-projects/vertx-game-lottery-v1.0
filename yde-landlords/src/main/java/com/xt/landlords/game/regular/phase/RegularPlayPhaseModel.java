package com.xt.landlords.game.regular.phase;

import com.xt.landlords.game.regular.GameRegularState;
import org.sunyata.octopus.model.GamePhaseModel;

/**
 * Created by leo on 17/5/16.
 */
public class RegularPlayPhaseModel extends GamePhaseModel<RegularPlayPhaseData> {

    public RegularPlayPhaseModel() {

    }

    public RegularPlayPhaseModel(String gameInstanceId) {
        super(gameInstanceId, GameRegularState.Playing.getValue(), 5);
    }

}
