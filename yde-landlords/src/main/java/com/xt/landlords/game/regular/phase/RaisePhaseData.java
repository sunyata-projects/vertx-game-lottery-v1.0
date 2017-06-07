package com.xt.landlords.game.regular.phase;

import com.xt.landlords.game.phase.TicketResult;
import org.sunyata.octopus.model.PhaseData;

/**
 * Created by leo on 17/5/16.
 */
public class RaisePhaseData extends PhaseData {
    private String betSerialNo;
    private int betAmt;

    public int getTimes() {
        return times;
    }

    public RaisePhaseData setTimes(int times) {
        this.times = times;
        return this;
    }

    private int times;
    private TicketResult ticketResult;


    public TicketResult getTicketResult() {
        return ticketResult;
    }

    public RaisePhaseData setTicketResult(TicketResult ticketResult) {
        this.ticketResult = ticketResult;
        return this;
    }

    public void setBetSerialNo(String betSerialNo) {
        this.betSerialNo = betSerialNo;
    }

    public String getBetSerialNo() {
        return betSerialNo;
    }

    public RaisePhaseData setBetAmt(int betAmt) {
        this.betAmt = betAmt;
        return this;
    }

    public int getBetAmt() {
        return betAmt;
    }
}