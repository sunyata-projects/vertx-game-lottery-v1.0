package com.xt.landlords.service;

import com.xt.landlords.account.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Created by leo on 17/5/26.
 */
@Component
public class ExchangeService {
    Logger logger = LoggerFactory.getLogger(ExchangeService.class);

    public ExchangeResult exchange(String userName, int amt, String gameType) {
        try {
            //messageId
            //timestamp
            //userName
            //gameId
            //amt
            BigDecimal money = new BigDecimal(amt).divide(new BigDecimal(100));
            logger.info("兑换的金额为:{}", money);
            Account.reductBalance(userName,money);
            return new ExchangeResult().setGamePoint(amt).setSerialNo("212321312312");
        } catch (Exception ex) {
            return new ExchangeResult().setErrorMessage(ex.getMessage());
        }
    }
}
