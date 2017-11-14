package com.xt.landlords.game.rank.phase;

import java.util.List;

/**
 * Created by leo on 17/5/16.
 */
public class RankPlayPhaseDealDataItem extends RankPlayPhaseDataItem {
    public List<Integer> getCenterCards() {
        return centerCards;
    }

    public RankPlayPhaseDealDataItem setCenterCards(List<Integer> centerCards) {
        this.centerCards = centerCards;
        return this;
    }

    public List<Integer> getRightCards() {
        return rightCards;
    }

    public RankPlayPhaseDealDataItem setRightCards(List<Integer> rightCards) {
        this.rightCards = rightCards;
        return this;
    }

    public List<Integer> getLeftCards() {
        return leftCards;
    }

    public RankPlayPhaseDealDataItem setLeftCards(List<Integer> leftCards) {
        this.leftCards = leftCards;
        return this;
    }

    public List<Integer> getUnderCards() {
        return underCards;
    }

    public RankPlayPhaseDealDataItem setUnderCards(List<Integer> underCards) {
        this.underCards = underCards;
        return this;
    }

    private List<Integer> centerCards;
    private List<Integer> rightCards;
    private List<Integer> leftCards;
    private List<Integer> underCards;


}