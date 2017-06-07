package com.xt.landlords.service;

import org.springframework.stereotype.Component;

/**
 * Created by leo on 17/5/26.
 */
@Component
public class ExchangeService {
    public ExchangeResult exchange(String userName, int amt, String gameType) {
        try {
            //messageId
            //timestamp
            //userName
            //gameId
            //amt
            return new ExchangeResult().setGamePoint(1000).setSerialNo("212321312312");
        } catch (Exception ex) {
            return new ExchangeResult().setErrorMessage(ex.getMessage());
        }
    }
}
