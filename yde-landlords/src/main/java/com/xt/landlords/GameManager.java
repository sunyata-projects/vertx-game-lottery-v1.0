package com.xt.landlords;


import com.xt.landlords.event.UserLeftEventMessage;
import com.xt.landlords.game.phase.BetPhaseData;
import com.xt.landlords.game.phase.BetPhaseModel;
import com.xt.landlords.game.puzzle.GamePuzzleModel;
//import com.xt.landlords.game.puzzle.GamePuzzlePhaseName;
import com.xt.landlords.game.puzzle.GamePuzzleState;
import com.xt.landlords.game.regular.GameRegularModel;
import com.xt.landlords.game.regular.GameRegularPhaseName;
import com.xt.landlords.game.regular.GameRegularState;
import com.xt.landlords.message.MessageClient;
import com.xt.landlords.statemachine.GameController;
import com.xt.landlords.statemachine.GameStateControllerFactory;
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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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
        if (gameType == GameTypes.Regular.getValue()) {
            GameRegularState state = (GameRegularState) gameControllerState;
            return GameStateControllerFactory.createGameRegularController(state);
        } else if (gameType == GameTypes.Point.getValue()) {

        } else if (gameType == GameTypes.Puzzle.getValue()) {
            GamePuzzleState state = (GamePuzzleState) gameControllerState;
            return GameStateControllerFactory.createGamePuzzleController(state);
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

    @Autowired
    MessageClient messageClient;

    public void syncGameModel(GameModel gameModel) throws Exception {
//        EventBus.getDistributePubsubStore().publish(SyncGameModelMessage.EventType, new SyncGameModelMessage()
//                .setGameModel(Json.encode(gameModel)));
        messageClient.asyncSaveGameModel(gameModel);

    }

    public GameModel createGameModelAndBetPhase(int gameType, String userName, String gameInstanceId, int betAmt) {
        GameModel gameModel = null;
        if (gameType == GameTypes.Regular.getValue()) {
            gameModel = new GameRegularModel(gameInstanceId);
            gameModel.setUserName(userName);
            GamePhaseModel gamePhaseModel = new BetPhaseModel(gameInstanceId, GameRegularPhaseName.Bet.getValue(), 1)
                    .setPhaseData
                            (new BetPhaseData()
                                    .setBetAmt(betAmt));
            gameModel.addOrUpdatePhase(gamePhaseModel);
        } else if (gameType == GameTypes.Point.getValue()) {

        } else if (gameType == GameTypes.Puzzle.getValue()) {
            gameModel = new GamePuzzleModel(gameInstanceId);
            gameModel.setUserName(userName);
            GamePhaseModel gamePhaseModel = new BetPhaseModel(gameInstanceId, GamePuzzleState.Bet.getValue(), 1)
                    .setPhaseData
                            (new BetPhaseData()
                                    .setBetAmt(betAmt));
            gameModel.addOrUpdatePhase(gamePhaseModel);

        } else {

        }
        return gameModel;
    }
}
