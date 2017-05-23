//package com.xt.yde.job;
//
////import com.xt.yde.job.model.Game;
////import com.xt.yde.job.model.Phase;
//
//import com.xt.yde.job.message.RetryProcessMessageService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import org.sunyata.octopus.json.Json;
//import org.sunyata.octopus.message.ComplexMessageInfo;
//import org.sunyata.octopus.model.GameModel;
//import org.sunyata.octopus.model.GamePhaseModel;
//
//import java.sql.Timestamp;
//
///**
// * Created by leo on 17/5/22.
// */
//@Component
//public class MyRunner implements CommandLineRunner {
//    @Autowired
//    GameModelStore gameModelStore;
//    @Autowired
//    RetryProcessMessageService retryProcessMessageService;
//
//    @Override
//    public void run(String... args) throws Exception {
//        GameModel gameModel = new GameModel() {
//            @Override
//            public Object getBetEvent() {
//                return null;
//            }
//
//            @Override
//            public Object getInitState() {
//                return null;
//            }
//
//            @Override
//            public boolean needBreakPlay() {
//                return false;
//            }
//        }.setGameInstanceId("sdfasdf").setGameType(1).setCreateDateTime(new Timestamp(System.currentTimeMillis()))
//                .setUserName("lcl");
//        GamePhaseModel gamePhaseModel = new GamePhaseModel("sdfasdf", "bet", 1);
//        gameModel.addPhase(gamePhaseModel);
//        //gameModelStore.create(gameModel, gamePhaseModel);
//
//        retryProcessMessageService.process(new ComplexMessageInfo().setBodyJsonString(Json.encode(gameModel)));
//    }
//}
