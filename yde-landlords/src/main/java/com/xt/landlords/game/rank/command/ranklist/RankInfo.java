package com.xt.landlords.game.rank.command.ranklist;

import java.math.BigDecimal;

/**
 * Created by leo on 17/10/24.
 */
public class RankInfo {
    public String getDisplayName() {
        return displayName;
    }

    public RankInfo setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public RankInfo setMoney(BigDecimal money) {
        this.money = money;
        return this;
    }

    public BigDecimal getScore() {
        return score;
    }

    public RankInfo setScore(BigDecimal score) {
        this.score = score;
        return this;
    }


    private String displayName;
    private BigDecimal money;
    private BigDecimal score;

}
