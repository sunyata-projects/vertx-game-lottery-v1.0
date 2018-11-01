package com.xt.landlords.service;

import java.math.BigDecimal;

/**
 * Created by leo on 17/9/5.
 */
public class RankPrizeLevelInfo {
    public int getPrizeLevel() {
        return prizeLevel;
    }

    public RankPrizeLevelInfo setPrizeLevel(int prizeLevel) {
        this.prizeLevel = prizeLevel;
        return this;
    }

    public BigDecimal getScore() {
        return score;
    }

    public RankPrizeLevelInfo setScore(BigDecimal score) {
        this.score = score;
        return this;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public RankPrizeLevelInfo setMoney(BigDecimal money) {
        this.money = money;
        return this;
    }

    public int getBombNumbers() {
        return bombNumbers;
    }

    public RankPrizeLevelInfo setBombNumbers(int bombNumbers) {
        this.bombNumbers = bombNumbers;
        return this;
    }

    private int prizeLevel;
    private BigDecimal score;
    private BigDecimal money;
    private int bombNumbers;
}
