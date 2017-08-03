package com.xt.landlords.game.eliminate.command;

import com.xt.landlords.*;
import com.xt.landlords.game.eliminate.GameEliminateEvent;
import com.xt.landlords.game.eliminate.GameEliminateModel;
import com.xt.landlords.game.eliminate.GameEliminateState;
import com.xt.landlords.game.eliminate.phase.EliminateClearPhaseData;
import com.xt.landlords.game.eliminate.phase.EliminatePlayPhaseData;
import com.xt.landlords.game.eliminate.phase.EliminatePlayPhaseDataItem;
import com.xt.landlords.game.eliminate.phase.EliminatePlayPhaseModel;
import com.xt.landlords.statemachine.GameController;
import com.xt.yde.protobuf.eliminate.Eliminate;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.ImmutableState;
import org.sunyata.octopus.OctopusRequest;
import org.sunyata.octopus.OctopusResponse;

//import com.xt.landlords.game.Eliminate.GameEliminatePhaseName;

/**
 * Created by leo on 17/5/15.
 */
@Component(Commands.EliminateClear)
public class EliminateClearCommandHandler extends AbstractGameControllerCommandHandler {
    org.slf4j.Logger logger = LoggerFactory.getLogger(EliminateClearCommandHandler.class);

    class CommandErrorCode {

    }

    @Override
    public void execute(OctopusRequest request, OctopusResponse response) throws Exception {
        try {
            if (!this.canAccpet(request, GameEliminateEvent.GameOver)) {
                response.setErrorCode(CommonCommandErrorCode.CanNotAcceptEventException);
                return;
            }
            GameController gameController = GameManager.getGameController(request.getSession().getCurrentUser()
                    .getName());
            if (gameController == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }
            GameEliminateModel gameModel = (GameEliminateModel) gameController.getGameModel();
            if (gameModel == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }

            //是否满足结算条件
            EliminatePlayPhaseModel phase = (EliminatePlayPhaseModel) gameModel.getPhase(GameEliminateState.Play
                    .getValue());
            if (phase != null) {
                EliminatePlayPhaseData phaseData = phase.getPhaseData();
                if (phaseData != null) {
                    EliminatePlayPhaseDataItem lastPlayPhaseDataItem = phaseData.getLastPlayPhaseDataItem();
                    if (lastPlayPhaseDataItem != null) {
                        if (lastPlayPhaseDataItem.getTotalDoubleKingCount() != 7) {
                            response.setErrorCode(CommonCommandErrorCode.CanNotAcceptEventException);
                            return;
                        }
                    }
                }
            }

            gameModel.addClearPhase();

            Eliminate.EliminateClearGameResponseMsg.Builder builder = Eliminate.EliminateClearGameResponseMsg
                    .newBuilder();
            gameController.fire(GameEliminateEvent.GameOver, gameModel);
            EliminateClearPhaseData phaseData = (EliminateClearPhaseData) gameController.getPhaseData
                    (GameEliminateState.GameOver.getValue());
            int totalMoney = phaseData.getTotalMoney();
            builder.setTotalMoney(totalMoney);
            //Account.addBalance(request.getSession().getCurrentUser().getName(), totalMoney);
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

