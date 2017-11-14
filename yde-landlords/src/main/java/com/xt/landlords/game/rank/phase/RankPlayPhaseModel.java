package com.xt.landlords.game.rank.phase;

import org.sunyata.octopus.model.GamePhaseModel;

/**
 * Created by leo on 17/5/16.
 */
public class RankPlayPhaseModel extends GamePhaseModel<RankPlayPhaseData> {

    private int rankIndex;

    public RankPlayPhaseModel() {

    }

    public RankPlayPhaseModel(String gameInstanceId, String phaseName, int orderBy) {
        super(gameInstanceId, phaseName, orderBy);
    }

    public void setRankIndex(int rankIndex) {
        this.rankIndex = rankIndex;
    }

    public int getRankIndex() {
        return rankIndex;
    }


}
