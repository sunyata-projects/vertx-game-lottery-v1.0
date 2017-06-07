package com.xt.landlords.service;

/**
 * Created by leo on 17/6/1.
 */
public class ExchangeResult {
    public String getSerialNo() {
        return serialNo;
    }

    public ExchangeResult setSerialNo(String serialNo) {
        this.serialNo = serialNo;
        return this;
    }

    public int getGamePoint() {
        return gamePoint;
    }

    public ExchangeResult setGamePoint(int gamePoint) {
        this.gamePoint = gamePoint;
        return this;
    }

    private String  serialNo;
    private int gamePoint;

    public String getErrorMessage() {
        return errorMessage;
    }

    public ExchangeResult setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    private String errorMessage;
}
