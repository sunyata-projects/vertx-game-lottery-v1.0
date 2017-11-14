package com.xt.landlords.game.classic.phase;

import org.sunyata.octopus.model.PhaseData;

import java.math.BigDecimal;

/**
 * Created by leo on 17/5/16.
 */
public class ClassicClearPhaseData extends PhaseData {

    private String serialNo;

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public ClassicClearPhaseData setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
        return this;
    }

    private BigDecimal totalMoney;//获得的总奖金

    public ClassicClearPhaseData setSerialNo(String serialNo) {
        this.serialNo = serialNo;
        return this;
    }

    public String getSerialNo() {
        return serialNo;
    }
}