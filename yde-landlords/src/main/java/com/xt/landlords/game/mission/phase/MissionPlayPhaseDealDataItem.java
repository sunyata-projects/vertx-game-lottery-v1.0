package com.xt.landlords.game.mission.phase;

import java.util.List;

/**
 * Created by leo on 17/5/16.
 */
public class MissionPlayPhaseDealDataItem extends MissionPlayPhaseDataItem {
    public List<Integer> getCenterCards() {
        return centerCards;
    }

    public MissionPlayPhaseDealDataItem setCenterCards(List<Integer> centerCards) {
        this.centerCards = centerCards;
        return this;
    }

    public List<Integer> getRightCards() {
        return rightCards;
    }

    public MissionPlayPhaseDealDataItem setRightCards(List<Integer> rightCards) {
        this.rightCards = rightCards;
        return this;
    }

    public List<Integer> getLeftCards() {
        return leftCards;
    }

    public MissionPlayPhaseDealDataItem setLeftCards(List<Integer> leftCards) {
        this.leftCards = leftCards;
        return this;
    }

    public List<Integer> getUnderCards() {
        return underCards;
    }

    public MissionPlayPhaseDealDataItem setUnderCards(List<Integer> underCards) {
        this.underCards = underCards;
        return this;
    }

    private List<Integer> centerCards;
    private List<Integer> rightCards;
    private List<Integer> leftCards;
    private List<Integer> underCards;


}