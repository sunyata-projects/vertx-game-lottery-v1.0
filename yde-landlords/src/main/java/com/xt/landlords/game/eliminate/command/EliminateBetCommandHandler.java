package com.xt.landlords.game.eliminate.command;

import com.xt.landlords.*;
import com.xt.landlords.game.eliminate.GameEliminateEvent;
import com.xt.landlords.game.eliminate.GameEliminateModel;
import com.xt.landlords.statemachine.GameController;
import com.xt.yde.protobuf.common.Common;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.ImmutableState;
import org.sunyata.octopus.OctopusRequest;
import org.sunyata.octopus.OctopusResponse;

//import com.xt.landlords.game.Eliminate.GameEliminatePhaseName;

/**
 * Created by leo on 17/5/15.
 */
@Component(Commands.EliminateGamePointBet)
public class EliminateBetCommandHandler extends AbstractGameControllerCommandHandler {
    org.slf4j.Logger logger = LoggerFactory.getLogger(EliminateBetCommandHandler.class);

    class CommandErrorCode {

    }

    @Override
    public void execute(OctopusRequest request, OctopusResponse response) throws Exception {
        try {
            if (!this.canAccpet(request, GameEliminateEvent.Bet)) {
                response.setErrorCode(CommonCommandErrorCode.CanNotAcceptEventException);
                return;
            }
            GameController gameController = GameManager.getGameController(request.getSession().getCurrentUser()
                    .getName());
            if (gameController == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }
            GameEliminateModel gameModel = (GameEliminateModel) gameController.getGameModel();// storeManager
            if (gameModel == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }
            //如果已经奖可以奖金关
            if (gameModel.isCompleteAwardCondition()) {
                response.setErrorCode(CommonCommandErrorCode.CanNotAcceptEventException);
                return;
            }
            //点数下注
            Common.GamePointBetRequestMsg gamePointBetRequestMsg = Common.GamePointBetRequestMsg.parseFrom(request
                    .getMessage().getBody());
            Common.GamePointBetResponseMsg.Builder builder = Common.GamePointBetResponseMsg.newBuilder();
            gameModel.addOrUpdatePlayPhase(gamePointBetRequestMsg.getGamePoint());
            gameController.fire(GameEliminateEvent.Bet, gameModel);
            ImmutableState currentRawState = gameController.getCurrentRawState();
            logger.info("{}:currentState:{}", this.getClass().getName(), currentRawState);
            response.setBody(builder.build().toByteArray());
        } catch (Exception ex) {
            ExceptionProcessor.process(response, ex);
        } finally {
            response.writeAndFlush();
        }
    }

    @Override
    public boolean isAsync() {
        return false;
    }
}

