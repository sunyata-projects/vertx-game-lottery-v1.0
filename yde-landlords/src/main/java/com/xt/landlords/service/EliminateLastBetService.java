package com.xt.landlords.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.quark.client.IdWorker;
import org.sunyata.quark.client.QuarkClient;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by leo on 17/5/16.
 */
@Component
public class EliminateLastBetService {
    private IdWorker worker = new IdWorker(0, 0);

    Logger logger = LoggerFactory.getLogger(EliminateLastBetService.class);
    HashMap<Integer, Float> map = new HashMap<>();

    public EliminateLastBetService() {
        map.put(0, 0f);
        map.put(1, 100f);
        map.put(2, 50f);
        map.put(3, 10f);
        map.put(4, 9f);
        map.put(5, 8f);
        map.put(6, 7f);
        map.put(7, 6f);
        map.put(8, 5f);
        map.put(9, 4f);
        map.put(10, 3f);
        map.put(11, 2f);
        map.put(12, 1.8f);
        map.put(13, 1.6f);
        map.put(14, 1.4f);
        map.put(15, 1.2f);
        map.put(16, 1f);
        map.put(17, 0.8f);
        map.put(18, 0.6f);
        map.put(19, 0.5f);
        map.put(20, 0.4f);

    }

    Random random = new Random();

    public int nextInt(int from, int to) {
        int max = to;
        int min = from;
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    @Autowired
    QuarkClient quarkClient;


    public EliminateLastBetResult bet(String userName, int betGamePoint,int totalAwardGamePoint, String gameInstanceId,
                                      boolean isZhiZun)
            throws
            Exception {
//        try {
//            String serialNo = String.valueOf(worker.nextId());
//            int i = nextInt(0, 20);
//            Float aFloat = map.get(i);
//            int totalMoney = (int) (aFloat * (betGamePoint / 100.00));
//            return new EliminateLastBetResult().setSerialNo(serialNo).setAwardLevel(i).setTotalMoney(totalMoney);
//        } catch (Exception ex) {
//            return new EliminateLastBetResult().setErrorMessage(ex.getMessage());
//        }
        if (isZhiZun) {
            return betForZhiZun(userName, betGamePoint, gameInstanceId);
        } else {
            return bet(userName, totalAwardGamePoint, gameInstanceId);
        }
    }


    public EliminateLastBetResult bet(String userName, int totalAwardGamePoint, String gameInstanceId) throws
            Exception {
        try {
            String serialNo = String.valueOf(worker.nextId());
            //int i = nextInt(0, 20);
//            Float money = map.get(i);
//            logger.info("消除赛100点时中奖金额为:{}", money);
            BigDecimal totalMoney = new BigDecimal(String.valueOf(totalAwardGamePoint)).divide(new BigDecimal("100"));
            logger.info("消除赛换算实际中奖金额为:{}", totalMoney);
            return new EliminateLastBetResult().setSerialNo(serialNo).setAwardLevel(1).setTotalMoney(totalMoney);
        } catch (Exception ex) {
            return new EliminateLastBetResult().setErrorMessage(ex.getMessage());
        }
    }

    public EliminateLastBetResult betForZhiZun(String userName, int betGamePoint, String gameInstanceId) throws
            Exception {
//        throw new Exception("下注失败");
        try {
            String serialNo = String.valueOf(worker.nextId());
//            int i = nextInt(0, 20);
//            Float aFloat = map.get(i);
//            int totalMoney = (int) (aFloat * (betGamePoint / 100.00));
            return new EliminateLastBetResult().setSerialNo(serialNo).setAwardLevel(99).setTotalMoney(new BigDecimal
                    (1000000));
        } catch (Exception ex) {
            return new EliminateLastBetResult().setErrorMessage(ex.getMessage());
        }
    }
}
