package com.xt.landlords.game.classic.phase;

import org.sunyata.octopus.model.PhaseData;

import java.math.BigDecimal;

/**
 * Created by leo on 17/5/16.
 */
public class ClassicTurnPhaseData extends PhaseData {

    private BigDecimal money;
    private BigDecimal score;

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public BigDecimal getScore() {
        return score;
    }
}