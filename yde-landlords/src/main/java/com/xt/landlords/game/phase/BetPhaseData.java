package com.xt.landlords.game.phase;

import org.sunyata.octopus.model.PhaseData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 17/5/16.
 */
public class BetPhaseData extends PhaseData {
    private String betSerialNo;
    private int betAmt;



    private List<TicketResult> ticketResults = new ArrayList<>();

    public TicketResult getTicketResult() {
        if (ticketResults.size() > 0) {
            return ticketResults.get(0);
        }
        return null;
    }

    public List<TicketResult> getTicketResults() {
        return ticketResults;
    }

    public BetPhaseData setTicketResult(TicketResult ticketResult) {
        //this.ticketResult = ticketResult;
        ticketResults.add(ticketResult);
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