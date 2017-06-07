package com.xt.landlords.game.eliminate.phase;

import org.sunyata.octopus.model.PhaseData;

import java.util.List;

/**
 * Created by leo on 17/5/16.
 */
public class EliminatePlayPhaseDataItem extends PhaseData {

    public int getBetGamePoint() {
        return betGamePoint;
    }

    public EliminatePlayPhaseDataItem setBetGamePoint(int betGamePoint) {
        this.betGamePoint = betGamePoint;
        return this;
    }

    public int getAwardGamePoint() {
        return awardGamePoint;
    }

    public EliminatePlayPhaseDataItem setAwardGamePoint(int awardGamePoint) {
        this.awardGamePoint = awardGamePoint;
        return this;
    }

    public int getTotalDoubleKingCount() {
        return totalDoubleKingCount;
    }

    public EliminatePlayPhaseDataItem setTotalDoubleKingCount(int totalDoubleKingCount) {
        this.totalDoubleKingCount = totalDoubleKingCount;
        return this;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public EliminatePlayPhaseDataItem setOrderBy(int orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public String getCardId() {
        return cardId;
    }

    public EliminatePlayPhaseDataItem setCardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    public int getDoubleKingCount() {
        return doubleKingCount;
    }

    public EliminatePlayPhaseDataItem setDoubleKingCount(int doubleKingCount) {
        this.doubleKingCount = doubleKingCount;
        return this;
    }

    public int getTotalAwardGamePoint() {
        return totalAwardGamePoint;
    }

    public EliminatePlayPhaseDataItem setTotalAwardGamePoint(int totalAwardGamePoint) {
        this.totalAwardGamePoint = totalAwardGamePoint;
        return this;
    }

    public boolean isOver() {
        return isOver;
    }

    public EliminatePlayPhaseDataItem setOver(boolean over) {
        isOver = over;
        return this;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public EliminatePlayPhaseDataItem setSerialNo(String serialNo) {
        this.serialNo = serialNo;
        return this;
    }

    public int getAwardLevel() {
        return awardLevel;
    }

    public EliminatePlayPhaseDataItem setAwardLevel(int awardLevel) {
        this.awardLevel = awardLevel;
        return this;
    }

    private String serialNo;//本次投注流水号
    private int betGamePoint;//本次投注点数
    private int awardLevel;//本次投注获得奖等
    private int awardGamePoint;//本次投注赢得的点数
    private int doubleKingCount = 0;//本次投注双王的数量
    private int totalAwardGamePoint;//本次投注完成后,赢得的总点数
    private int totalDoubleKingCount = 0;//本次投注完成后,双王总数
    private int exchangeGamePointBalance;//剩余兑换点数
    private int orderBy;//次序
    private boolean isOver;//本次item是否完成

    private List<List<Integer>> cards;


    private String cardId;//本次投注获得的牌库id

    public void setCards(List<List<Integer>> cards) {
        this.cards = cards;
    }

    public List<List<Integer>> getCards() {
        return cards;
    }

    public int getExchangeGamePointBalance() {
        return exchangeGamePointBalance;
    }

    public EliminatePlayPhaseDataItem setExchangeGamePointBalance(int exchangeGamePointBalance) {
        this.exchangeGamePointBalance = exchangeGamePointBalance;
        return this;
    }
}