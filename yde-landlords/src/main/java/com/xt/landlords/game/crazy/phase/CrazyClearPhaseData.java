package com.xt.landlords.game.crazy.phase;

import org.sunyata.octopus.model.PhaseData;

import java.math.BigDecimal;

/**
 * Created by leo on 17/5/16.
 */
public class CrazyClearPhaseData extends PhaseData {

    private String serialNo;

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public CrazyClearPhaseData setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
        return this;
    }

    private BigDecimal totalMoney;//获得的总奖金

    public CrazyClearPhaseData setSerialNo(String serialNo) {
        this.serialNo = serialNo;
        return this;
    }

    public String getSerialNo() {
        return serialNo;
    }
}