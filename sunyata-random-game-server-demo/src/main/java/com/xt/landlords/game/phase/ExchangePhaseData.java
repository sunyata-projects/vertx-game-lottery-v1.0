package com.xt.landlords.game.phase;

import org.sunyata.octopus.model.PhaseData;

/**
 * Created by leo on 17/5/16.
 */
public class ExchangePhaseData extends PhaseData {

    private String serialNo;//序列号
    private int amt;//金额
    private int gamePoint;//换取的游戏点数
    private int savedPoint;//原有游戏没有完成保存的点数

    public int getSavedPoint() {
        return savedPoint;
    }

    public ExchangePhaseData setSavedPoint(int savedPoint) {
        this.savedPoint = savedPoint;
        return this;
    }


    public int getAmt() {
        return amt;
    }

    public ExchangePhaseData setAmt(int amt) {
        this.amt = amt;
        return this;
    }

    public int getGamePoint() {
        return gamePoint;
    }

    public ExchangePhaseData setGamePoint(int gamePoint) {
        this.gamePoint = gamePoint;
        return this;
    }


    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getSerialNo() {
        return serialNo;
    }
}