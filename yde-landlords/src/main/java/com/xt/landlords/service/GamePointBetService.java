package com.xt.landlords.service;

import com.xt.landlords.ai.core.util.myutil.AIUtil;
import com.xt.landlords.ai.core.util.myutil.CardType;
import com.xt.yde.thrift.card.eliminate.EliminateCards;
import com.xt.yde.thrift.card.eliminate.EliminateCardsService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.quark.client.IdWorker;
import org.sunyata.quark.client.QuarkClient;
import org.sunyata.spring.thrift.client.annotation.ThriftClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public GamePointBetResult bet(String userName, int betAmt, String gameInstanceId) throws Exception {
//        throw new Exception("下注失败");
        try {
            String serialNo = String.valueOf(worker.nextId());
            EliminateCards cards = cardService.getCards(1, 2);
            //int result = 0;
            List<Integer> result = new ArrayList<>();
            int totalGamePoint = 0;
            for (int i = 0; i < cards.getCards().size(); i++) {
                if (i % 2 != 0) {//奇数
                    List<Integer> integers = cards.getCards().get(i);
                    totalGamePoint += calculatorGamePointByLength(integers);
                    result.addAll(integers);
                }

            }
            long count52 = result.stream().filter(p -> p.equals(52)).count();
            long count53 = result.stream().filter(p -> p.equals(53)).count();
            int bombNum = (int) (count52 > count53 ? count52 : count53);
            logger.info("双王数量:{}", bombNum);
            totalGamePoint = totalGamePoint == 0 ? 0 : (int) (totalGamePoint * (betAmt / 100.00));
            return new GamePointBetResult().setSerialNo(serialNo).setAwardLevel(2).setDoubleKingCount(bombNum)
                    .setAwardGamePoint(totalGamePoint).setCards(cards.getCards());
        } catch (Exception ex) {
            logger.error(ExceptionUtils.getStackTrace(ex));
            return new GamePointBetResult().setErrorMessage(ex.getMessage());
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
    }
}
