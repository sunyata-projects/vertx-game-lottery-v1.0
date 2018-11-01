package com.xt.landlords.game.classic.command;

import com.xt.landlords.*;
import com.xt.landlords.game.classic.GameClassicEvent;
import com.xt.landlords.game.classic.GameClassicModel;
import com.xt.landlords.game.classic.GameClassicState;
import com.xt.landlords.game.classic.phase.ClassicClearPhaseData;
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
@Component(Commands.ClassicClear)
public class ClassicClearCommandHandler extends AbstractGameControllerCommandHandler {
    org.slf4j.Logger logger = LoggerFactory.getLogger(ClassicClearCommandHandler.class);

    class CommandErrorCode {

    }

    @Override
    public void execute(OctopusRequest request, OctopusResponse response) throws Exception {
        try {
            if (!this.canAccpet(request, GameClassicEvent.GameOver)) {
                response.setErrorCode(CommonCommandErrorCode.CanNotAcceptEventException);
                return;
            }
            GameController gameController = GameManager.getGameController(request.getSession().getCurrentUser()
                    .getName());
            if (gameController == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }
            GameClassicModel gameModel = (GameClassicModel) gameController.getGameModel();
            if (gameModel == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }
            //是否满足结算条件

            gameModel.addClearPhase();

            Common.ClearGameResponseMsg.Builder builder = Common.ClearGameResponseMsg.newBuilder();
            gameController.fire(GameClassicEvent.GameOver, gameModel);
            ClassicClearPhaseData phaseData = (ClassicClearPhaseData) gameController.getPhaseData(GameClassicState.GameOver
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

