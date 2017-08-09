package com.xt.landlords.service;

import com.xt.landlords.game.phase.TicketResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.sunyata.quark.client.IdWorker;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by leo on 17/5/16.
 */
@Component
public class GuessSizeBetService {
    private IdWorker worker = new IdWorker(0, 0);
    Logger logger = LoggerFactory.getLogger(GuessSizeBetService.class);

//    @Autowired
//    QuarkClient quarkClient;

    Random r = new Random();

    public TicketResult betAndQueryPrizeLevel(String userName, String gameInstanceId) {
        String serialNo = String.valueOf(worker.nextId());
        int i = r.nextInt(7);
        if (i % 2 == 0) {
            return new TicketResult().setTicketId(serialNo).setPrizeCash(BigDecimal.ZERO).setPrizeLevel(-1);
        }
        return new TicketResult().setTicketId(serialNo).setPrizeCash(new BigDecimal("10")).setPrizeLevel(1);
    }
}
