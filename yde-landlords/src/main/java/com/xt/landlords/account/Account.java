package com.xt.landlords.account;

import com.xt.landlords.GameManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by leo on 17/7/14.
 */
public class Account {
    static Logger logger = LoggerFactory.getLogger(Account.class);
    static Map<String, BigDecimal> maps = new HashMap<>();

    public static BigDecimal getBalance(String name) {
        BigDecimal accountBalance = BigDecimal.ZERO;
        if (!maps.containsKey(name)) {
            maps.put(name, new BigDecimal("1000"));
        }
        accountBalance = maps.getOrDefault(name, new BigDecimal("0"));
        logger.info("帐户金额为:{}", accountBalance);
        return accountBalance;
    }

    public static void addBalance(String name, BigDecimal amt) {
        BigDecimal accountBalance = BigDecimal.ZERO;
        if (!maps.containsKey(name)) {
            maps.put(name, new BigDecimal("1000"));
        }
        accountBalance = maps.getOrDefault(name, new BigDecimal("0"));
        logger.info("帐户资金原有{}",accountBalance);
        logger.info("帐户资金增加{}",amt);
        accountBalance = accountBalance.add(amt);
        maps.put(name, accountBalance);
        logger.info("帐户资金增加后:{}", accountBalance);
        GameManager.notifyBalanceChange(name, accountBalance);
    }

    public static void reductBalance(String name, BigDecimal amt) {
        BigDecimal accountBalance = BigDecimal.ZERO;
        if (!maps.containsKey(name)) {
            maps.put(name, new BigDecimal("1000"));
        }
        accountBalance = maps.getOrDefault(name, new BigDecimal("0"));
        //accountBalance -= amt;
        accountBalance = accountBalance.subtract(amt);
        maps.put(name, accountBalance);
        logger.info("帐户资金减少:{}", accountBalance);
        GameManager.notifyBalanceChange(name, accountBalance);
    }

    public static void refresh(String name) {
        maps.remove(name);
    }
}
