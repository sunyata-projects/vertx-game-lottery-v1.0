package com.xt.landlords.game.regular.command;

import com.xt.landlords.*;
import com.xt.landlords.account.Account;
import com.xt.landlords.game.regular.GameRegularEvent;
import com.xt.landlords.game.regular.GameRegularModel;
import com.xt.landlords.game.regular.GameRegularState;
import com.xt.landlords.game.regular.phase.RegularClearPhaseData;
import com.xt.landlords.statemachine.GameController;
import com.xt.yde.protobuf.regular.GameRegular;
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
@Component(Commands.RegularClear)
public class RegularClearCommandHandler extends AbstractGameControllerCommandHandler {
    org.slf4j.Logger logger = LoggerFactory.getLogger(RegularClearCommandHandler.class);

    class CommandErrorCode {

    }

    @Override
    public void execute(OctopusRequest request, OctopusResponse response) throws Exception {
        try {
            if (!this.canAccpet(request, GameRegularEvent.GameOver)) {
                response.setErrorCode(CommonCommandErrorCode.CanNotAcceptEventException);
                return;
            }
            GameController gameController = GameManager.getGameController(request.getSession().getCurrentUser()
                    .getName());
            if (gameController == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }
            GameRegularModel gameModel = (GameRegularModel) gameController.getGameModel();
            if (gameModel == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }
            gameModel.addClearPhase();

            GameRegular.RegularClearGameResponseMsg.Builder builder = GameRegular.RegularClearGameResponseMsg
                    .newBuilder();
            gameController.fire(GameRegularEvent.GameOver, gameModel);
            RegularClearPhaseData phaseData = (RegularClearPhaseData) gameController.getPhaseData(GameRegularState.GameOver.getValue());
            BigDecimal totalMoney = phaseData.getTotalMoney();
            builder.setTotalMoney(Float.parseFloat(totalMoney.toPlainString()));
            Account.addBalance(request.getSession().getCurrentUser().getName(), totalMoney);
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

