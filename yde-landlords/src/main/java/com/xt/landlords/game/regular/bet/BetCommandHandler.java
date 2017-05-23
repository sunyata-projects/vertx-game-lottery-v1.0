package com.xt.landlords.game.regular.bet;

import com.xt.landlords.*;
import com.xt.landlords.game.regular.GameRegularEvent;
import com.xt.landlords.game.regular.GameRegularPhaseName;
import com.xt.landlords.statemachine.GameController;
import com.xt.yde.protobuf.common.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.octopus.OctopusRequest;
import org.sunyata.octopus.OctopusResponse;
import org.sunyata.octopus.model.GameModel;
import org.sunyata.octopus.model.GamePhaseModel;
import org.sunyata.octopus.model.PhaseState;
import org.sunyata.quark.client.IdWorker;

/**
 * Created by leo on 17/4/18.
 */
@Component(Commands.Bet)
public class BetCommandHandler extends AbstractAuthCommandHandler {
    class CommandErrorCode {

    }

    @Override
    public boolean onExecuteBefore(OctopusRequest request, OctopusResponse response) {
        boolean flag = super.onExecuteBefore(request, response);
        if (!flag) {
            return flag;
        }
        return true;
        //1 gameModel == null and gameController != null //这种情况是redis重启,而游戏服务器正常
    }

    @Autowired
    BetService betService;

    @Autowired
    GameManager gameManager;

    @Autowired
    StoreManager storeManager;

    @Override
    public void execute(OctopusRequest request, OctopusResponse response) throws Exception {
        try {
            String userName = request.getSession().getCurrentUser().getName();
            Common.BetRequestMsg betRequestMsg = Common.BetRequestMsg.parseFrom(request.getMessage()
                    .getBody());
            //是否可接受bet Event
            GameController gameController = GameManager.getGameController(userName);
            if (gameController != null) {
                boolean canAccept = gameController.canAccept(GameRegularEvent.Bet);
                if (!canAccept) {
                    response.setErrorCode(CommonCommandErrorCode.CanNotAcceptEventException);
                    return;
                }
            }

            GameModel gameModel = storeManager.getGameModelFromCache(userName);

            if (gameModel == null) {
                IdWorker worker = new IdWorker(0, 0);
                String gameInstanceId = String.valueOf(worker.nextId());
                gameModel = gameManager.createGameModel(betRequestMsg.getGameType(), userName, gameInstanceId,
                        betRequestMsg
                                .getAmt());
                storeManager.storeGameModel(userName, gameModel);
            } else {
                GamePhaseModel phase = gameModel.getPhase(GameRegularPhaseName.Bet.getValue());
                if (phase.getPhaseState() == PhaseState.Success) {//下注成功
                    response.setErrorCode(CommonCommandErrorCode.CanNotRepeatBet);
                    return;
                }
            }

            if (gameController == null) {
                gameController = GameManager.createGameController(betRequestMsg.getGameType(), gameModel,
                        (GameControllerState) gameModel.getInitState());
                GameManager.put(userName, gameController);
                gameController.start();
            }
            gameController.fire(gameModel.getBetEvent(), gameModel);
            Common.BetResponseMsg build = Common.BetResponseMsg.newBuilder().setGameId(gameModel
                    .getGameInstanceId()).build();
            response.setBody(build.toByteArray());
        } catch (Exception ex) {
            //response.setErrorCode(CommonCommandErrorCode.RemoteAccessError);
            ExceptionProcessor.process(response, ex);
        } finally {
            response.writeAndFlush();
        }
    }

    @Override
    public boolean isAsync() {
        return true;
    }
}
