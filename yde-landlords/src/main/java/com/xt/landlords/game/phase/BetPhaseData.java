package com.xt.landlords.game.phase;

import org.sunyata.octopus.model.PhaseData;

/**
 * Created by leo on 17/5/16.
 */
public class BetPhaseData extends PhaseData {
    private String betSerialNo;
    private int betAmt;
    private TicketResult ticketResult;


    public TicketResult getTicketResult() {
        return ticketResult;
    }

    public BetPhaseData setTicketResult(TicketResult ticketResult) {
        this.ticketResult = ticketResult;
        return this;
    }

    public int getBetAmt() {
        return betAmt;
    }

    public BetPhaseData setBetAmt(int betAmt) {
        this.betAmt = betAmt;
        return this;
    }


    public void setBetSerialNo(String betSerialNo) {
        this.betSerialNo = betSerialNo;
    }

    public String getBetSerialNo() {
        return betSerialNo;
    }
}