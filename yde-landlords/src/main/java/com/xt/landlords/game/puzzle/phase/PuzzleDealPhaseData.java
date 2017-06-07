package com.xt.landlords.game.puzzle.phase;

import org.sunyata.octopus.model.PhaseData;

/**
 * Created by leo on 17/5/16.
 */
public class PuzzleDealPhaseData extends PhaseData {


    public int getTotalMoney() {
        return totalMoney;
    }

    public PuzzleDealPhaseData setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
        return this;
    }

    private int totalMoney;

    public String getCardId() {
        return cardId;
    }

    public PuzzleDealPhaseData setCardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    private String cardId;


}