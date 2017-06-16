package com.xt.landlords.game.regular.phase;

import org.sunyata.octopus.model.PhaseData;

/**
 * Created by leo on 17/5/16.
 */
public class RegularClearPhaseData extends PhaseData {

    private String serialNo;

    public int getTotalMoney() {
        return totalMoney;
    }

    public RegularClearPhaseData setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
        return this;
    }

    private int totalMoney;//获得的总奖金

    public RegularClearPhaseData setSerialNo(String serialNo) {
        this.serialNo = serialNo;
        return this;
    }

    public String getSerialNo() {
        return serialNo;
    }
}