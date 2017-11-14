package com.xt.landlords.service;

import java.math.BigDecimal;

/**
 * Created by leo on 17/9/5.
 */
public class TurnResultInfo {
    private BigDecimal score;
    private BigDecimal money;

    public BigDecimal getScore() {
        return score;
    }

    public TurnResultInfo setScore(BigDecimal score) {
        this.score = score;
        return this;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public TurnResultInfo setMoney(BigDecimal money) {
        this.money = money;
        return this;
    }
}
