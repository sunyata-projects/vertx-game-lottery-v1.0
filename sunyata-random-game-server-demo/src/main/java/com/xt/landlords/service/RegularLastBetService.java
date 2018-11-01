package com.xt.landlords.service;

import com.xt.landlords.GameManager;
import com.xt.landlords.account.Account;
import com.xt.landlords.game.phase.TicketResult;
import com.xt.landlords.statemachine.GameController;
import com.xt.yde.GameTypes;
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
public class RegularLastBetService {
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

    HashMap<Integer, String> crazyMap = new HashMap<>();

    public RegularLastBetService() {
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

        crazyMap.put(0, "0");
        crazyMap.put(1, "500");
        crazyMap.put(2, "50");
        crazyMap.put(3, "10");
        crazyMap.put(4, "4");
        crazyMap.put(5, "3");
        crazyMap.put(6, "2");
        crazyMap.put(7, "1.5");
        crazyMap.put(8, "10000");//至尊牌型

    }

    public TicketResult betAndQueryPrizeLevel(int gameType, String userName, int betAmt, String gameInstanceId)
            throws Exception {
//        throw new Exception("下注失败");
        GameController gameController = GameManager.getGameController(userName);
        GameModel gameModel = gameController.getGameModel();
        //Account.reductBalance(gameModel.getUserName(), new BigDecimal(betAmt));
        String serialNo = String.valueOf(worker.nextId());
        if (gameType == GameTypes.Regular.getValue()) {
            return new TicketResult().setTicketId(serialNo).setPrizeType(2).setPrizeLevel(99).setPrizeCash
                    (new BigDecimal(String.valueOf(betAmt)).multiply(new BigDecimal("10000")));
        }
        return null;
    }


    public TicketResult betAndQueryPrizeLevelForCrazyGuessSize(int type, BigDecimal betAmt, String userName)
            throws Exception {
//        throw new Exception("下注失败");
        GameController gameController = GameManager.getGameController(userName);
        GameModel gameModel = gameController.getGameModel();
        Account.reductBalance(gameModel.getUserName(), betAmt);
        String serialNo = String.valueOf(worker.nextId());
        int random = nextInt(0, 1);
        if (random == 0) {
            return new TicketResult().setTicketId(serialNo).setPrizeType(2).setPrizeLevel(1).setPrizeCash(betAmt);
        } else {
            return new TicketResult().setTicketId(serialNo).setPrizeType(2).setPrizeLevel(-1).setPrizeCash(BigDecimal
                    .ZERO);
        }
    }

    public String bet(int gameType, String userName, int betAmt, String gameInstanceId) throws Exception {
        String serialNo = String.valueOf(worker.nextId());
        return serialNo;
    }
}
