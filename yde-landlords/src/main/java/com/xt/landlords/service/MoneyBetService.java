package com.xt.landlords.service;

import com.xt.landlords.GameTypes;
import com.xt.landlords.game.phase.TicketResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.quark.client.IdWorker;
import org.sunyata.quark.client.QuarkClient;

import java.util.Random;

/**
 * Created by leo on 17/5/16.
 */
@Component
public class MoneyBetService {
    private IdWorker worker = new IdWorker(0, 0);

    @Autowired
    QuarkClient quarkClient;

    Random random = new Random();

    public TicketResult betAndQueryPrizeLevel(int gameType, String userName, int betAmt, String gameInstanceId)
            throws Exception {
//        throw new Exception("下注失败");
        String serialNo = String.valueOf(worker.nextId());
        if (gameType == GameTypes.Regular.getValue()) {
            //return serialNo;
            //奖等 1,2,3,4,5,6
            int prizeLevel = random.nextInt(6);
            int cash = 0;
            if (prizeLevel == 1) {
                cash = 1000;
            } else if (prizeLevel == 2) {
                cash = 500;
            } else if (prizeLevel == 3) {
                cash = 200;
            } else if (prizeLevel == 4) {
                cash = 150;
            } else if (prizeLevel == 5) {
                cash = 0;
            } else if (prizeLevel == 6) {
                cash = 0;
            }
            return new TicketResult().setTicketId(serialNo).setPrizeType(2).setPrizeLevel(prizeLevel).setPrizeCash
                    (cash);
        } else if (gameType == GameTypes.Puzzle.getValue()) {
            return new TicketResult().setTicketId(serialNo).setPrizeType(2).setPrizeLevel(2).setPrizeCash
                    (1000);
        }
//        HashMap<String, String> parameters = new HashMap<>();
//        parameters.put("betAmt", String.valueOf(betAmt));
//        JsonResponseResult createResult = quarkClient.create(String.valueOf(serialNo), BusinessComponents
//                .LandLordsBetComponent, userName, String.valueOf(gameInstanceId), Json.encode(parameters), false);
//        if (createResult.getCode() == 0) {
//            JsonResponseResult betResult = quarkClient.runByManual(String.valueOf(serialNo), 1, null);
//            if (betResult.getCode() == 0) {
//                return serialNo;
//            }
//        }
//        return null;
        return null;
    }

    public String bet(int gameType, String userName, int betAmt, String gameInstanceId) throws Exception {
        String serialNo = String.valueOf(worker.nextId());
        return serialNo;
    }
}
