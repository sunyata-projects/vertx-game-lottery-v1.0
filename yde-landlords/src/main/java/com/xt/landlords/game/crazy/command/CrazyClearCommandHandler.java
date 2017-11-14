package com.xt.landlords.game.crazy.command;

import com.xt.landlords.*;
import com.xt.landlords.game.crazy.GameCrazyEvent;
import com.xt.landlords.game.crazy.GameCrazyModel;
import com.xt.landlords.game.crazy.GameCrazyState;
import com.xt.landlords.game.crazy.phase.CrazyClearPhaseData;
import com.xt.landlords.statemachine.GameController;
import com.xt.yde.protobuf.common.Common;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.ImmutableState;
import org.sunyata.octopus.OctopusRequest;
import org.sunyata.octopus.OctopusResponse;

import java.math.BigDecimal;

//import com.xt.landlords.game.Eliminate.GameEliminatePhaseName;

/**
 * Created by leo on 17/5/15.
 */
@Component(Commands.CrazyClear)
public class CrazyClearCommandHandler extends AbstractGameControllerCommandHandler {
    org.slf4j.Logger logger = LoggerFactory.getLogger(CrazyClearCommandHandler.class);

    class CommandErrorCode {

    }

    @Override
    public void execute(OctopusRequest request, OctopusResponse response) throws Exception {
        try {
            if (!this.canAccpet(request, GameCrazyEvent.GameOver)) {
                response.setErrorCode(CommonCommandErrorCode.CanNotAcceptEventException);
                return;
            }
            GameController gameController = GameManager.getGameController(request.getSession().getCurrentUser()
                    .getName());
            if (gameController == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }
            GameCrazyModel gameModel = (GameCrazyModel) gameController.getGameModel();
            if (gameModel == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }

            //是否满足结算条件


            gameModel.addClearPhase();

            Common.ClearGameResponseMsg.Builder builder = Common.ClearGameResponseMsg.newBuilder();
            gameController.fire(GameCrazyEvent.GameOver, gameModel);
            CrazyClearPhaseData phaseData = (CrazyClearPhaseData) gameController.getPhaseData(GameCrazyState.GameOver
                    .getValue());
            BigDecimal totalMoney = phaseData.getTotalMoney();
            builder.setMoney(totalMoney.toPlainString());
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

