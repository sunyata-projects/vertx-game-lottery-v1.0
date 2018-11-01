package com.xt.landlords;


import com.xt.landlords.event.UserLeftEventMessage;
import com.xt.landlords.game.classic.GameClassicModel;
import com.xt.landlords.game.classic.GameClassicState;
import com.xt.landlords.game.phase.BetPhaseData;
import com.xt.landlords.game.phase.BetPhaseModel;
import com.xt.landlords.message.MessageClient;
import com.xt.landlords.statemachine.GameController;
import com.xt.landlords.statemachine.GameControllerFactory;
import com.xt.yde.GameTypes;
import com.xt.yde.protobuf.common.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.octopus.EventBus;
import org.sunyata.octopus.OctopusResponse;
import org.sunyata.octopus.Session;
import org.sunyata.octopus.SessionManager;
import org.sunyata.octopus.model.GameModel;
import org.sunyata.octopus.model.GamePhaseModel;
import org.sunyata.quark.client.IdWorker;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

//import com.xt.landlords.game.puzzle.GamePuzzlePhaseName;

/**
 * Created by leo on 17/4/27.
 */
@Component
public class GameManager {
    private final static ConcurrentMap<String, GameController> games = new ConcurrentHashMap<>();
    static Logger logger = LoggerFactory.getLogger(GameManager.class);
//    public static GameModel newGameModel() {
//        return new GameModel();
//    }

    public static void put(String accoundId, GameController gameController) throws Exception {
        GameController orDefault = games.getOrDefault(accoundId, null);
        if (orDefault != null) {
            throw new Exception("已经存在进行中的游戏");
        }
        games.put(accoundId, gameController);
    }

    public static void onUserLeft(String accountId) {
        EventBus.getLocalPubsubStore().publish(UserLeftEventMessage.EventType, new UserLeftEventMessage());
        games.remove(accountId);
    }


    public static void onGameOver(String accountId) {
        games.remove(accountId);
    }

    public static GameController getGameController(String accountId) {
        return games.getOrDefault(accountId, null);
    }

    public static GameController createGameController(int gameType, GameControllerState
            gameControllerState) {
        if (gameType == GameTypes.Classic.getValue()) {
            GameClassicState state = (GameClassicState) gameControllerState;
            return GameControllerFactory.createGameClassicController(state);

        } else {

        }
        return null;
    }


    public static boolean exist(String accountId) {
        return games.containsKey(accountId);
    }

    public void kickPlayer(String userName) {
        Session session = SessionManager.getSession(userName);
        if (session == null) {
            logger.info("目标用户不存在,{}无法被踢除", userName);
            return;
        }
        new OctopusResponse(session.getHandlerContext(), Integer.parseInt(Commands.KickPlayer), 0).writeAndFlush();
        session.getHandlerContext().channel().close();
    }
//    public GameModel createGameModelAndExchangePhase(int gameType, String userName, int betAmt) {
//        GameModel gameModel = null;
//        IdWorker worker = new IdWorker(0, 0);
//        String gameInstanceId = String.valueOf(worker.nextId());
//        if (gameType == GameTypes.Eliminate.getValue()) {
//            gameModel = new GameEliminateModel(gameInstanceId);
//            gameModel.setUserName(userName);
//            GamePhaseModel gamePhaseModel = new ExchangePhaseModel(gameInstanceId, GameCommonState.Exchange
//                    .getValue(), 1)
//                    .setPhaseData
//                            (new ExchangePhaseData().setAmt(betAmt));
//            gameModel.addOrUpdatePhase(gamePhaseModel);
//        } else if (gameType == GameTypes.Mission.getValue()) {
//
//        } else if (gameType == GameTypes.Puzzle.getValue()) {
//            gameModel = new GamePuzzleModel(gameInstanceId);
//            gameModel.setUserName(userName);
//            GamePhaseModel gamePhaseModel = new BetPhaseModel(gameInstanceId, GamePuzzleState.Bet.getValue(), 1)
//                    .setPhaseData
//                            (new BetPhaseData()
//                                    .setBetAmt(betAmt));
//            gameModel.addOrUpdatePhase(gamePhaseModel);
//
//        } else {
//
//        }
//        return gameModel;
//    }
    public static void notifyBalanceChange(String userName, BigDecimal balance) {
        Session session = SessionManager.getSession(userName);
        if (session == null) {
            logger.info("目标用户不存在,{}无法被踢除", userName);
            return;
        }
        OctopusResponse octopusResponse = new OctopusResponse(session.getHandlerContext(), Integer.parseInt(Commands
                .NotifyBalanceChanged), 0);
        Common.BalanceChangedResponseMsg.Builder builder = Common.BalanceChangedResponseMsg.newBuilder();
        builder.setBalance(balance.toPlainString());
        Common.BalanceChangedResponseMsg responseMsg = builder.build();
        octopusResponse.setBody(responseMsg.toByteArray());
        octopusResponse.writeAndFlush();
    }

    @Autowired
    MessageClient messageClient;

    public void syncGameModel(GameModel gameModel) throws Exception {
//        EventBus.getDistributePubsubStore().publish(SyncGameModelMessage.EventType, new SyncGameModelMessage()
//                .setContext(Json.encode(gameModel)));
        messageClient.asyncSaveGameModel(gameModel);

    }

    public void saveGameModelToCacheAndAsyncDb(GameModel gameModel) throws Exception {
        storeManager.storeGameModel(gameModel.getUserName(), gameModel);
        messageClient.asyncSaveGameModel(gameModel);
    }

    public GameModel createGameModelAndBetPhase(int gameType, String userName, int betAmt) {
        GameModel gameModel = null;
        IdWorker worker = new IdWorker(0, 0);
        String gameInstanceId = String.valueOf(worker.nextId());

        if (gameType == GameTypes.Classic.getValue()) {
            gameModel = new GameClassicModel(gameInstanceId);
            gameModel.setUserName(userName);
            GamePhaseModel gamePhaseModel = new BetPhaseModel(gameInstanceId, GameClassicState.Bet.getValue(), 1)
                    .setPhaseData
                            (new BetPhaseData()
                                    .setBetAmt(betAmt));
            gameModel.addOrUpdatePhase(gamePhaseModel);

        } else {

        }
        return gameModel;
    }


    @Autowired
    StoreManager storeManager;

    public GameModel getFromCacheOrCreate(int gameType, String userName, int betAmt) {
        GameModel gameModel = storeManager.getGameModelFromCache(userName);
        if (gameModel == null) {
            gameModel = createGameModelAndBetPhase(gameType, userName, betAmt);
            storeManager.storeGameModel(userName, gameModel);
        }
        return gameModel;
    }

    public void clearGameModelFromCache(String userName) {
        storeManager.storeGameModel(userName, null);
    }
}
