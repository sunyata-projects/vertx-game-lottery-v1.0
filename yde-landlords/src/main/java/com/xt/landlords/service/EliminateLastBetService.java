package com.xt.landlords.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.quark.client.IdWorker;
import org.sunyata.quark.client.QuarkClient;

/**
 * Created by leo on 17/5/16.
 */
@Component
public class EliminateLastBetService {
    private IdWorker worker = new IdWorker(0, 0);

    @Autowired
    QuarkClient quarkClient;

    public EliminateLastBetResult bet(String userName, int betAmt, String gameInstanceId) throws Exception {
//        throw new Exception("下注失败");
        try {
            String serialNo = String.valueOf(worker.nextId());
            return new EliminateLastBetResult().setSerialNo(serialNo).setAwardLevel(2).setTotalMoney(1000);
        } catch (Exception ex) {
            return new EliminateLastBetResult().setErrorMessage(ex.getMessage());
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
