package com.xt.landlords.game.puzzle.phase;

import org.sunyata.octopus.model.GamePhaseModel;

/**
 * Created by leo on 17/5/16.
 */
public class PuzzleDealPhaseModel extends GamePhaseModel<PuzzleDealPhaseData> {

    public PuzzleDealPhaseModel() {

    }

    public PuzzleDealPhaseModel(String gameInstanceId, String phaseName, int orderBy) {
        super(gameInstanceId, phaseName, orderBy);
    }

}
