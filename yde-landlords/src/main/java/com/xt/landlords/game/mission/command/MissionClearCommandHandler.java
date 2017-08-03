package com.xt.landlords.game.mission.command;

import com.xt.landlords.*;
import com.xt.landlords.account.Account;
import com.xt.landlords.game.mission.GameMissionEvent;
import com.xt.landlords.game.mission.GameMissionModel;
import com.xt.landlords.game.mission.GameMissionState;
import com.xt.landlords.game.mission.phase.MissionClearPhaseData;
import com.xt.landlords.statemachine.GameController;
import com.xt.yde.protobuf.mission.Mission;
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
@Component(Commands.MissionClear)
public class MissionClearCommandHandler extends AbstractGameControllerCommandHandler {
    org.slf4j.Logger logger = LoggerFactory.getLogger(MissionClearCommandHandler.class);

    class CommandErrorCode {

    }

    @Override
    public void execute(OctopusRequest request, OctopusResponse response) throws Exception {
        try {
            if (!this.canAccpet(request, GameMissionEvent.GameOver)) {
                response.setErrorCode(CommonCommandErrorCode.CanNotAcceptEventException);
                return;
            }
            GameController gameController = GameManager.getGameController(request.getSession().getCurrentUser()
                    .getName());
            if (gameController == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }
            GameMissionModel gameModel = (GameMissionModel) gameController.getGameModel();
            if (gameModel == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }
            gameModel.addClearPhase();

            Mission.MissionClearGameResponseMsg.Builder builder = Mission.MissionClearGameResponseMsg
                    .newBuilder();
            gameController.fire(GameMissionEvent.GameOver, gameModel);
            MissionClearPhaseData phaseData = (MissionClearPhaseData) gameController.getPhaseData(GameMissionState
                    .GameOver.getValue());
            //BetPhaseData betPhaseData = (BetPhaseData) gameController.getPhaseData(GameMissionState.Bet.getValue());
            float totalMoney = phaseData.getTotalMoney();
            builder.setTotalMoney(totalMoney);
            Account.addBalance(request.getSession().getCurrentUser().getName(), new BigDecimal( totalMoney));
            builder.setTimes(new BigDecimal( gameModel.getTimes()).toPlainString());
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

