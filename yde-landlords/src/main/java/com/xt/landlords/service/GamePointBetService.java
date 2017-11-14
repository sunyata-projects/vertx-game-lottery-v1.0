package com.xt.landlords.service;

import com.xt.landlords.ai.core.util.myutil.AIUtil;
import com.xt.landlords.ai.core.util.myutil.CardType;
import com.xt.yde.thrift.card.eliminate.EliminateCardsService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.quark.client.IdWorker;
import org.sunyata.quark.client.QuarkClient;
import org.sunyata.spring.thrift.client.annotation.ThriftClient;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by leo on 17/5/16.
 */
@Component
public class GamePointBetService {
    private IdWorker worker = new IdWorker(0, 0);
    Logger logger = LoggerFactory.getLogger(GamePointBetService.class);

    @Autowired
    QuarkClient quarkClient;

    @ThriftClient(serviceId = "yde-card-service", path = "/eliminate")
    EliminateCardsService.Client cardService;

    static HashMap<Integer, Integer> map = new HashMap<>();

    static HashMap<Integer, Integer> mapPoints = new HashMap<>();

    public GamePointBetService() {
        map.put(7, 20);
        map.put(8, 50);
        map.put(9, 80);
        map.put(10, 100);
        map.put(11, 200);
        map.put(12, 500);
        map.put(14, 800);
        map.put(15, 1000);
        map.put(16, 5000);
        map.put(18, 10000);
//        map.put(20, 0);


        mapPoints.put(1, 100000);
        mapPoints.put(2, 80000);
        mapPoints.put(3, 70000);
        mapPoints.put(4, 50000);
        mapPoints.put(5, 20000);
        mapPoints.put(6, 10000);
        mapPoints.put(7, 8000);
        mapPoints.put(8, 7000);
        mapPoints.put(9, 5000);
        mapPoints.put(10, 2000);
        mapPoints.put(11, 1000);
        mapPoints.put(12, 900);
        mapPoints.put(13, 800);
        mapPoints.put(14, 700);
        mapPoints.put(15, 650);
        mapPoints.put(16, 600);
        mapPoints.put(17, 550);
        mapPoints.put(18, 500);
        mapPoints.put(19, 480);
        mapPoints.put(20, 460);
        mapPoints.put(21, 440);
        mapPoints.put(22, 420);
        mapPoints.put(23, 400);
        mapPoints.put(24, 380);
        mapPoints.put(25, 360);
        mapPoints.put(26, 340);
        mapPoints.put(27, 320);
        mapPoints.put(28, 300);
        mapPoints.put(29, 280);
        mapPoints.put(30, 260);
        mapPoints.put(31, 240);
        mapPoints.put(32, 220);
        mapPoints.put(33, 200);
        mapPoints.put(34, 190);
        mapPoints.put(35, 180);
        mapPoints.put(36, 170);
        mapPoints.put(37, 160);
        mapPoints.put(38, 150);
        mapPoints.put(39, 140);
        mapPoints.put(40, 130);
        mapPoints.put(41, 120);
        mapPoints.put(42, 110);
        mapPoints.put(43, 100);
        mapPoints.put(44, 90);
        mapPoints.put(45, 80);
        mapPoints.put(46, 70);
        mapPoints.put(47, 60);
        mapPoints.put(48, 50);
        mapPoints.put(49, 40);
        mapPoints.put(50, 20);
        mapPoints.put(0, 0);
    }

    public int calculatorGamePointByLength(List<Integer> cards) {

        int length = cards.size();
        if (length < 7) {
            return 0;
        }
        CardType cardType = AIUtil.jugdeType(cards);
        if (length == 20) {
            if (cardType == CardType.c1122) {
                return 100000;
            } else if (cardType == CardType.c11122234) {
                return 50000;
            } else if (cardType == CardType.c1112223344) {
                return 80000;
            }
        } else {
            //if (Arrays.asList(CardType.c422, CardType.c123, CardType.c111222).contains(cardType)) {
            return map.getOrDefault(length, 0);
            //}
        }
        return 0;
    }

    Random random = new Random();

    public int nextInt(int from, int to) {
        int max = to;
        int min = from;
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }


    public GamePointBetResult bet(String userName, int betAmt, String gameInstanceId) throws Exception {
        try {
            int prizeLevel = 0;
            String serialNo = String.valueOf(worker.nextId());
            int bombNumsRandom = 0;
            Integer totalGamePoint = 0;
            if (betAmt > 100) {//如果投注额大于100,则有机会中累计奖
                prizeLevel = nextInt(0, 55);
            } else {
                prizeLevel = nextInt(0, 50);
            }
//            prizeLevel = 51;
            if (prizeLevel <= 50) {
                totalGamePoint = mapPoints.getOrDefault(prizeLevel, 0);
                totalGamePoint = totalGamePoint == 0 ? 0 : (int) (totalGamePoint * (betAmt / 100.00));
                bombNumsRandom = nextInt(2, 2);//又王的中奖机率为100%
            }


            boolean bombFlag = bombNumsRandom == 2;
            logger.info("双王数量:{}", bombFlag ? 1 : 0);
            //如果大于50,则视为中至尊牌型
            if (prizeLevel > 50) {//则视为中至尊牌型
                prizeLevel = 99;
            }

            GamePointBetResult gamePointBetResult = new GamePointBetResult().setSerialNo(serialNo).setAwardLevel
                    (prizeLevel)
                    .setDoubleKingCount(bombFlag ? 1 : 0)
                    .setAwardGamePoint(totalGamePoint);//.setCards(cards.getCards());
            return gamePointBetResult;
        } catch (Exception ex) {
            logger.error(ExceptionUtils.getStackTrace(ex));
            return new GamePointBetResult().setErrorMessage(ex.getMessage());
        }
    }

//    public GamePointBetResult bet(String userName, int betAmt, String gameInstanceId) throws Exception {
//        try {
//            String serialNo = String.valueOf(worker.nextId());
//            EliminateCards cards = cardService.getCards(1, 2);
//            //int result = 0;
//            List<Integer> result = new ArrayList<>();
//            int totalGamePoint = 0;
//            for (int i = 0; i < cards.getCards().size(); i++) {
//                if (i % 2 != 0) {//奇数
//                    List<Integer> integers = cards.getCards().get(i);
//                    totalGamePoint += calculatorGamePointByLength(integers);
//                    result.addAll(integers);
//                }
//
//            }
//            long count52 = result.stream().filter(p -> p.equals(52)).count();
//            long count53 = result.stream().filter(p -> p.equals(53)).count();
//            int bombNum = (int) (count52 > count53 ? count52 : count53);
//            logger.info("双王数量:{}", bombNum);
//            totalGamePoint = totalGamePoint == 0 ? 0 : (int) (totalGamePoint * (betAmt / 100.00));
//            return new GamePointBetResult().setSerialNo(serialNo).setAwardLevel(2).setDoubleKingCount(bombNum)
//                    .setAwardGamePoint(totalGamePoint).setCards(cards.getCards());
//        } catch (Exception ex) {
//            logger.error(ExceptionUtils.getStackTrace(ex));
//            return new GamePointBetResult().setErrorMessage(ex.getMessage());
//        }
//    }
}
