package com.xt.landlords.service;

import java.math.BigDecimal;

/**
 * Created by leo on 17/6/1.
 */
public class EliminateLastBetResult {
    private String serialNo;//下注序列号
    private int awardLevel;//奖等
    private BigDecimal totalMoney;//获得的总奖金
    private String errorMessage;

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public EliminateLastBetResult setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
        return this;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public EliminateLastBetResult setSerialNo(String serialNo) {
        this.serialNo = serialNo;
        return this;
    }

    public int getAwardLevel() {
        return awardLevel;
    }

    public EliminateLastBetResult setAwardLevel(int awardLevel) {
        this.awardLevel = awardLevel;
        return this;
    }

    public EliminateLastBetResult setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
