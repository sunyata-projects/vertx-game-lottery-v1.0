package com.xt.landlords.game.phase;

/**
 * Created by leo on 17/6/2.
 */
public class TicketResult {
    private String ticketId;
    private int status;
    private int prizeType;//1：未中奖；2：小奖；3：大奖
    private int prizeLevel;
    private String prizeContent;
    private int prizeCash;
    private int prizeInfaceCash;
    private int prizePoint;

    public int getPrizePoint() {
        return prizePoint;
    }

    public TicketResult setPrizePoint(int prizePoint) {
        this.prizePoint = prizePoint;
        return this;
    }

    public int getPrizeInfaceCash() {
        return prizeInfaceCash;
    }

    public TicketResult setPrizeInfaceCash(int prizeInfaceCash) {
        this.prizeInfaceCash = prizeInfaceCash;
        return this;
    }

    public int getPrizeCash() {
        return prizeCash;
    }

    public TicketResult setPrizeCash(int prizeCash) {
        this.prizeCash = prizeCash;
        return this;
    }

    public String getPrizeContent() {
        return prizeContent;
    }

    public TicketResult setPrizeContent(String prizeContent) {
        this.prizeContent = prizeContent;
        return this;
    }

    public int getPrizeLevel() {
        return prizeLevel;
    }

    public TicketResult setPrizeLevel(int prizeLevel) {
        this.prizeLevel = prizeLevel;
        return this;
    }

    public int getPrizeType() {
        return prizeType;
    }

    public TicketResult setPrizeType(int prizeType) {
        this.prizeType = prizeType;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public TicketResult setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getTicketId() {
        return ticketId;
    }

    public TicketResult setTicketId(String ticketId) {
        this.ticketId = ticketId;
        return this;
    }
}
