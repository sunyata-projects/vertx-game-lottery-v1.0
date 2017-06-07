package com.xt.landlords.service;

import java.util.List;

/**
 * Created by leo on 17/6/1.
 */
public class GamePointBetResult {
    private String serialNo;//下注序列号
    private int awardLevel;//奖等
    private int awardGamePoint;//赢得的点数
    private int doubleKingCount;//又王数量
    private String errorMessage;
    private List<List<Integer>> cards;

    public List<List<Integer>> getCards() {
        return cards;
    }

    public GamePointBetResult setCards(List<List<Integer>> cards) {
        this.cards = cards;
        return this;
    }



    public String getSerialNo() {
        return serialNo;
    }

    public GamePointBetResult setSerialNo(String serialNo) {
        this.serialNo = serialNo;
        return this;
    }

    public int getAwardLevel() {
        return awardLevel;
    }

    public GamePointBetResult setAwardLevel(int awardLevel) {
        this.awardLevel = awardLevel;
        return this;
    }

    public int getAwardGamePoint() {
        return awardGamePoint;
    }

    public GamePointBetResult setAwardGamePoint(int awardGamePoint) {
        this.awardGamePoint = awardGamePoint;
        return this;
    }

    public int getDoubleKingCount() {
        return doubleKingCount;
    }

    public GamePointBetResult setDoubleKingCount(int doubleKingCount) {
        this.doubleKingCount = doubleKingCount;
        return this;
    }

    public GamePointBetResult setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
