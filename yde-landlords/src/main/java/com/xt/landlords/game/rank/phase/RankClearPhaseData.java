package com.xt.landlords.game.rank.phase;

import org.sunyata.octopus.model.PhaseData;

import java.math.BigDecimal;

/**
 * Created by leo on 17/5/16.
 */
public class RankClearPhaseData extends PhaseData {

    private String serialNo;

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public RankClearPhaseData setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
        return this;
    }

    private BigDecimal totalMoney;//获得的总奖金

    public RankClearPhaseData setSerialNo(String serialNo) {
        this.serialNo = serialNo;
        return this;
    }

    public String getSerialNo() {
        return serialNo;
    }
}