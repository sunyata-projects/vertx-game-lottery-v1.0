package com.xt.landlords.game.regular.phase;

import com.xt.landlords.game.phase.TicketResult;
import org.sunyata.octopus.model.PhaseData;

/**
 * Created by leo on 17/5/16.
 */
public class GuessSizePhaseData extends PhaseData {
    private String betSerialNo;
    private TicketResult ticketResult;

    public TicketResult getTicketResult() {
        return ticketResult;
    }

    public GuessSizePhaseData setTicketResult(TicketResult ticketResult) {
        this.ticketResult = ticketResult;
        return this;
    }

    public void setBetSerialNo(String betSerialNo) {
        this.betSerialNo = betSerialNo;
    }

    public String getBetSerialNo() {
        return betSerialNo;
    }
}