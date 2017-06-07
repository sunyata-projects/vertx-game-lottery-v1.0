package com.xt.landlords.service;

import com.xt.yde.thrift.card.eliminate.EliminateCards;
import com.xt.yde.thrift.card.eliminate.EliminateCardsService;
import info.developerblog.spring.thrift.annotation.ThriftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.quark.client.IdWorker;
import org.sunyata.quark.client.QuarkClient;

import java.util.ArrayList;
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

    public GamePointBetResult bet(String userName, int betAmt, String gameInstanceId) throws Exception {
//        throw new Exception("下注失败");
        try {
            String serialNo = String.valueOf(worker.nextId());
            EliminateCards cards = cardService.getCards(1, 2);
            //int result = 0;
            List<Integer> result = new ArrayList<>();
            for (int i = 0; i < cards.getCards().size(); i++) {
                if (i % 2 != 0) {//奇数
                    List<Integer> integers = cards.getCards().get(i);
                    result.addAll(integers);
                }
            }
            long count52 = result.stream().filter(p -> p.equals(52)).count();
            long count53 = result.stream().filter(p -> p.equals(53)).count();
            int bombNum = (int) (count52 > count53 ? count52 : count53);
            logger.info("双王数量:{}", bombNum);
            return new GamePointBetResult().setSerialNo(serialNo).setAwardLevel(2).setDoubleKingCount(bombNum)
                    .setAwardGamePoint(1000).setCards(cards.getCards());
        } catch (Exception ex) {
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
