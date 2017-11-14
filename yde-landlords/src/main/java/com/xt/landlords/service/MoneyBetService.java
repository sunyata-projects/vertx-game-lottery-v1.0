package com.xt.landlords.service;

import com.xt.landlords.GameManager;
import com.xt.landlords.account.Account;
import com.xt.landlords.game.phase.TicketResult;
import com.xt.landlords.statemachine.GameController;
import com.xt.yde.GameTypes;
import com.xt.yde.custom.CurrentCardConfig;
import com.xt.yde.custom.PrizeLevelConfigInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.octopus.model.GameModel;
import org.sunyata.quark.client.IdWorker;
import org.sunyata.quark.client.QuarkClient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by leo on 17/5/16.
 */
@Component
public class MoneyBetService {
    private IdWorker worker = new IdWorker(0, 0);

    Logger logger = LoggerFactory.getLogger(MoneyBetService.class);
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

    HashMap<Integer, Float> classicMap = new HashMap<>();

    HashMap<Integer, Integer> classicQuessSizeMap = new HashMap<>();

    HashMap<Integer, Integer> crazyQuessSizeMap = new HashMap<>();


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

        crazyMap.put(0, "0");
        crazyMap.put(1, "1000");//牌库里没有8炸的,所以先去掉
        crazyMap.put(2, "100");
        crazyMap.put(3, "50");
        crazyMap.put(4, "10");
        crazyMap.put(5, "5");
        crazyMap.put(6, "3");
        crazyMap.put(7, "1.5");
        crazyMap.put(8, "300000");//至尊牌型

        classicMap.put(1, 10.0f);
        classicMap.put(2, 3.0f);
        classicMap.put(3, 2.0f);
        classicMap.put(4, 1.5f);
        classicMap.put(5, 0.0f);
        classicMap.put(6, 300000.0f);

        classicQuessSizeMap.put(1, 1);
        classicQuessSizeMap.put(2, 1);
        classicQuessSizeMap.put(3, 1);
        classicQuessSizeMap.put(4, 1);
        classicQuessSizeMap.put(5, 1);


        crazyQuessSizeMap.put(1, 0);
        crazyQuessSizeMap.put(2, 0);
        crazyQuessSizeMap.put(3, 0);
        crazyQuessSizeMap.put(4, 0);
        crazyQuessSizeMap.put(5, 0);

    }


    @Autowired
    CurrentCardConfig currentCardConfig;

    private int getRegularPrizeLevel() {
        PrizeLevelConfigInfo prizeLevelConfigInfo = currentCardConfig.getPrizeLevelConfigInfo(GameTypes.Regular
                .getValue());
        if (prizeLevelConfigInfo != null) {
            Integer prizeRandom = prizeLevelConfigInfo.getPrizeRandom();
            return prizeRandom;
        }
        return this.nextInt(1, 6);
    }

    private int getMissionPrizeLevel() {

        PrizeLevelConfigInfo prizeLevelConfigInfo = currentCardConfig.getPrizeLevelConfigInfo(GameTypes.Mission
                .getValue());
        if (prizeLevelConfigInfo != null) {
            return prizeLevelConfigInfo.getPrizeRandom();
        }
        return nextInt(1, 15);
    }

    private int getClassicPrizeLevel() {

        PrizeLevelConfigInfo prizeLevelConfigInfo = currentCardConfig.getPrizeLevelConfigInfo(GameTypes.Classic
                .getValue());
        if (prizeLevelConfigInfo != null) {
            return prizeLevelConfigInfo.getPrizeRandom();
        }
//        if (is50Percent()) {
//            return 6;
//        } else {
        return nextInt(1, 6);
        //}
    }

    public boolean is50Percent() {
        int i = nextInt(1, 2);
        return i == 1;
    }

    private int getRankPrizeLevel(int index) {

        PrizeLevelConfigInfo prizeLevelConfigInfo = currentCardConfig.getPrizeLevelConfigInfo(GameTypes.Rank
                .getValue());
        if (prizeLevelConfigInfo != null) {
            return prizeLevelConfigInfo.getPrizeRandom();
        }
        //return 8;
        return nextInt(1, 8);
    }

    private int getCrazyPrizeLevel() {

        PrizeLevelConfigInfo prizeLevelConfigInfo = currentCardConfig.getPrizeLevelConfigInfo(GameTypes.Crazy
                .getValue());
        if (prizeLevelConfigInfo != null) {
            return prizeLevelConfigInfo.getPrizeRandom();
        }
        return nextInt(0, 8);
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
            int prizeLevel = getRegularPrizeLevel();
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
            int random = getMissionPrizeLevel(); //nextInt(1, 15);
            Float times = map.getOrDefault(random, 0.0f);
            BigDecimal cash = new BigDecimal(times.toString()).multiply(new BigDecimal(String.valueOf(betAmt)));
            return new TicketResult().setTicketId(serialNo).setPrizeType(2).setPrizeLevel(times).setPrizeCash(cash);

        } else if (gameType == GameTypes.Crazy.getValue()) {
            int random = -1;
            random = getCrazyPrizeLevel();

            String times = crazyMap.getOrDefault(random, "0");
            BigDecimal cash = BigDecimal.ZERO;
            if (random == 8) {
                cash = new BigDecimal(times);
            } else {
                cash = new BigDecimal(times).multiply(new BigDecimal(String.valueOf(betAmt)));
            }
            return new TicketResult().setTicketId(serialNo).setPrizeType(2).setPrizeLevel(random).setPrizeCash
                    (cash);
        } else if (gameType == GameTypes.Classic.getValue()) {
            int random = getClassicPrizeLevel();
            Float times = classicMap.getOrDefault(random, 0.0f);
            BigDecimal cash = BigDecimal.ZERO;

            if (random != 6) {
                cash = new BigDecimal(times.toString()).multiply(new BigDecimal(String.valueOf(betAmt)));
            } else {
                cash = new BigDecimal("300000.00");
            }
            return new TicketResult().setTicketId(serialNo).setPrizeType(2).setPrizeLevel(random).setPrizeCash
                    (cash);

        }
        return null;
    }

    @Autowired
    RankPrizeManager rankPrizeManager;

    public List<TicketResult> betAndQueryPrizeLevelWithMutiple(int gameType, String userName, int betAmt, String
            gameInstanceId)
            throws Exception {
//        throw new Exception("下注失败");
        GameController gameController = GameManager.getGameController(userName);
        GameModel gameModel = gameController.getGameModel();
        Account.reductBalance(gameModel.getUserName(), new BigDecimal(betAmt));
        String serialNo = String.valueOf(worker.nextId());
        List<TicketResult> results = new ArrayList<>();
        if (gameType == GameTypes.Rank.getValue()) {
            for (int i = 0; i < 3; i++) {
                int rankPrizeLevel = getRankPrizeLevel(i);
                RankPrizeLevelInfo rankPrizeLevelInfo = rankPrizeManager.getRankPrizeLevelInfo(rankPrizeLevel);
                TicketResult ticketResult = new TicketResult().setTicketId(serialNo).setPrizeType(2).setPrizeLevel
                        (rankPrizeLevelInfo.getPrizeLevel())
                        .setPrizeCash(rankPrizeLevelInfo.getMoney());
                results.add(ticketResult);
                logger.info("排位赛{}奖等:{},奖金:{}", userName, ticketResult.getPrizeLevel(), ticketResult.getPrizeCash
                        ());
            }
            return results;
        }
        return null;
    }
//
//    @Autowired
//    CurrentCardConfig currentCardConfig;

    public TicketResult betAndQueryPrizeLevelForCrazyGuessSize(int gameType, int type, BigDecimal betAmt, String
            userName, int idx)
            throws Exception {
        String serialNo = String.valueOf(worker.nextId());
        boolean open = currentCardConfig.isOpen();
        HashMap<Integer, Integer> maps = null;
        if (gameType == GameTypes.Crazy.getValue()) {
            maps = crazyQuessSizeMap;
        } else {
            maps = classicQuessSizeMap;
        }
        if (open) {
            if (maps.getOrDefault(idx, 0) == 1) {
                if (type == 0) {
                    return new TicketResult().setTicketId(serialNo).setPrizeType(2).setPrizeLevel(1).setPrizeCash
                            (betAmt);

                } else {
                    return new TicketResult().setTicketId(serialNo).setPrizeType(2).setPrizeLevel(1).setPrizeCash(betAmt
                            .divide(new BigDecimal("2")));
                }
            } else {
                return new TicketResult().setTicketId(serialNo).setPrizeType(2).setPrizeLevel(-1).setPrizeCash
                        (BigDecimal
                                .ZERO);
            }
        } else {

            int random = nextInt(1, 2);
            if (random == 1) {
                if (type == 0) {
                    return new TicketResult().setTicketId(serialNo).setPrizeType(2).setPrizeLevel(1).setPrizeCash
                            (betAmt);

                } else {
                    return new TicketResult().setTicketId(serialNo).setPrizeType(2).setPrizeLevel(1).setPrizeCash(betAmt
                            .divide(new BigDecimal("2")));
                }
            } else {
                return new TicketResult().setTicketId(serialNo).setPrizeType(2).setPrizeLevel(-1).setPrizeCash
                        (BigDecimal
                                .ZERO);
            }
        }
    }

    public String bet(int gameType, String userName, int betAmt, String gameInstanceId) throws Exception {
        String serialNo = String.valueOf(worker.nextId());
        return serialNo;
    }


//    public TicketResult betAndQueryPrizeLevelForClassicGuessSize(int type, BigDecimal betAmt, String userName, int
//            idx) {
//        GameController gameController = GameManager.getGameController(userName);
//        String serialNo = String.valueOf(worker.nextId());
//        int random = nextInt(1, 2);
//        if (random == 1) {
//            if (type == 0) {
//                return new TicketResult().setTicketId(serialNo).setPrizeType(2).setPrizeLevel(1).setPrizeCash(betAmt);
//            } else {
//                return new TicketResult().setTicketId(serialNo).setPrizeType(2).setPrizeLevel(1).setPrizeCash(betAmt
//                        .divide(new BigDecimal("2")));
//            }
//        } else {
//            return new TicketResult().setTicketId(serialNo).setPrizeType(2).setPrizeLevel(-1).setPrizeCash(BigDecimal
//                    .ZERO);
//        }
//    }
}
