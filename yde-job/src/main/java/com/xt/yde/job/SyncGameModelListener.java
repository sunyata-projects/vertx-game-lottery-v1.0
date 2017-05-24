//package com.xt.yde.job;
//
//import com.xt.yde.custom.SyncGameModelMessage;
//import com.xt.yde.job.model.Game;
//import com.xt.yde.job.model.Phase;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.sunyata.octopus.json.Json;
//import org.sunyata.octopus.model.PhaseState;
//import org.sunyata.octopus.store.pubsub.PubSubListener;
//
//
///**
// * Created by leo on 17/5/16.
// */
//@Component
//public class SyncGameModelListener implements PubSubListener<SyncGameModelMessage> {
//
//    Logger logger = LoggerFactory.getLogger(SyncGameModelListener.class);
//
//    @Autowired
//    GameModelStore gameModelStore;
//
//    @Override
//    public void onMessage(SyncGameModelMessage data) {
//        //// TODO: 17/5/16 写数据库
//        String gameModelString = data.getGameModel();
//        Game game1 = Json.decodeValue(gameModelString, Game.class);
//        int count = gameModel.getPhaseCount();
//        if (count == 0) {
//            logger.error("gameModel非法,不存在任何一个游戏阶段");
//            return;
//        }
//        if (count == 1) {
//            Phase phase = gameModel.getPhases().stream().filter(p -> p.getOrderBy() == count).findFirst
//                    ().orElse(null);
//            if (phase.getPhaseState() != PhaseState.Success) {
//                logger.error("阶段没有执行成功,无需存储");
//            } else {
//                gameModelStore.create(game1, phase);
//            }
//        } else if (count > 0) {
//            Phase phase = gameModel.getPhases().get(count - 1);
//            if (phaseModel.getPhaseState() != PhaseState.Success) {
//                logger.error("阶段没有执行成功,无需存储");
//            } else {
//                gameModelStore.addOrUpdatePhase(phaseModel);
//            }
//        }
//    }
//}