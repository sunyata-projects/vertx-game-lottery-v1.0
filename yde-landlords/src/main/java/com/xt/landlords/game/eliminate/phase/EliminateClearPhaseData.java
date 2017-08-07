package com.xt.landlords.game.eliminate.phase;

import org.sunyata.octopus.model.PhaseData;

/**
 * Created by leo on 17/5/16.
 */
public class EliminateClearPhaseData extends PhaseData {

    private String serialNo;

    public Integer getTotalMoney() {
        return totalMoney;
    }

    public EliminateClearPhaseData setTotalMoney(Integer totalMoney) {
        this.totalMoney = totalMoney;
        return this;
    }

    private Integer totalMoney;//获得的总奖金

    public EliminateClearPhaseData setSerialNo(String serialNo) {
        this.serialNo = serialNo;
        return this;
    }

    public String getSerialNo() {
        return serialNo;
    }
}