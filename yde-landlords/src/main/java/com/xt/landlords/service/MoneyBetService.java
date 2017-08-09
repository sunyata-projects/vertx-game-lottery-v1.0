package com.xt.landlords.service;

import com.xt.landlords.GameManager;
import com.xt.landlords.GameTypes;
import com.xt.landlords.account.Account;
import com.xt.landlords.game.phase.TicketResult;
import com.xt.landlords.statemachine.GameController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.octopus.model.GameModel;
import org.sunyata.quark.client.IdWorker;
import org.sunyata.quark.client.QuarkClient;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by leo on 17/5/16.
 */
@Component
public class MoneyBetService {
    private IdWorker worker = new IdWorker(0, 0);

    @Autowired
    GameManager gameManager;
    @Autowired
    QuarkClient quarkClient;

    Random random = new Random();

    public int nextInt(int from, int to) {
        int max = to;
        int min = from;
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    HashMap<Integer, Float> map = new HashMap<>();

    public MoneyBetService() {
        map.put(1, 1.5f);
        map.put(2, 3f);
        map.put(3, 6f);
        map.put(4, 50f);
        map.put(5, 100f);
        map.put(6, 200f);
        map.put(7, 1000f);
        map.put(8, 10000f);
        map.put(9, 0f);
        map.put(10, 0f);
        map.put(11, 0f);
        map.put(12, 0f);
        map.put(13, 0f);
        map.put(14, 0f);
        map.put(15, 0f);
    }

    public TicketResult betAndQueryPrizeLevel(int gameType, String userName, int betAmt, String gameInstanceId)
            throws Exception {
//        throw new Exception("下注失败");
        GameController gameController = GameManager.getGameController(userName);
        GameModel gameModel = gameController.getGameModel();
        Account.reductBalance(gameModel.getUserName(), new BigDecimal(betAmt));
        String serialNo = String.valueOf(worker.nextId());
        if (gameType == GameTypes.Regular.getValue()) {
            //return serialNo;
            //奖等 1,2,3,4,5,6
            int prizeLevel = this.nextInt(1, 6);
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
                    (new BigDecimal(String.valueOf(cash)));
        } else if (gameType == GameTypes.Puzzle.getValue()) {
            return new TicketResult().setTicketId(serialNo).setPrizeType(2).setPrizeLevel(2).setPrizeCash
                    (new BigDecimal("1000"));
        } else if (gameType == GameTypes.Mission.getValue()) {
            int random = nextInt(1, 15);
            Float times = map.getOrDefault(random, 0.0f);
            BigDecimal cash = new BigDecimal(times.toString()).multiply(new BigDecimal(String.valueOf(betAmt)));
            return new TicketResult().setTicketId(serialNo).setPrizeType(2).setPrizeLevel(times).setPrizeCash(cash);
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
