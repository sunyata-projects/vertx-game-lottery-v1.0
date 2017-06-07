package com.xt.landlords.game.command;

import com.xt.landlords.*;
import com.xt.landlords.service.MoneyBetService;
import com.xt.landlords.statemachine.GameController;
import com.xt.yde.protobuf.common.Common;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.ImmutableState;
import org.sunyata.octopus.OctopusRequest;
import org.sunyata.octopus.OctopusResponse;
import org.sunyata.octopus.model.GameModel;
import org.sunyata.octopus.model.GamePhaseModel;
import org.sunyata.octopus.model.PhaseState;

/**
 * Created by leo on 17/4/18.
 */
@Component(Commands.Bet)
public class BetCommandHandler extends AbstractAuthCommandHandler {
    org.slf4j.Logger logger = LoggerFactory.getLogger(BetCommandHandler.class);

    class CommandErrorCode {

    }

    @Autowired
    MoneyBetService moneyBetService;

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
            GameModel gameModel = null;

            GameController gameController = GameManager.getGameController(userName);
            if (gameController != null) {
                Integer gameType = gameController.getGameType();
                //游戏类型是否匹配
                if (!gameType.equals(betRequestMsg.getGameType())) {
                    response.setErrorCode(CommonCommandErrorCode.GameTypeError);
                    return;
                }
                //是否可接受bet Event
                boolean canAccept = gameController.canAcceptBetEvent();
                if (!canAccept) {
                    response.setErrorCode(CommonCommandErrorCode.CanNotAcceptEventException);
                    return;
                }
                gameModel = (GameModel) gameController.getGameModel();
                if (gameModel == null) {
                    response.setErrorCode(CommonCommandErrorCode.InternalError);
                    return;
                }
                GamePhaseModel phase = gameController.getPhase(GameCommonState.Bet.getValue());//.Bet.getValue());
                if (phase.getPhaseState() == PhaseState.Success) {//下注成功
                    response.setErrorCode(CommonCommandErrorCode.CanNotRepeatBet);
                    return;
                }
            } else {
                gameModel = gameManager.createGameModelAndBetPhase(betRequestMsg.getGameType(), userName, betRequestMsg
                        .getAmt());

                gameController = GameManager.createGameController(betRequestMsg.getGameType(), (GameControllerState)
                        gameModel.getLastSuccessState());
                gameController.setGameModel(gameModel);
                GameManager.put(userName, gameController);
                gameController.start();
            }

            gameController.fire(gameController.getFirstEvent(), gameModel);
            ImmutableState currentRawState = gameController.getCurrentRawState();
            logger.info("{}:currentState:{}", this.getClass().getName(), currentRawState);
            Common.BetResponseMsg build = Common.BetResponseMsg.newBuilder().build();
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
