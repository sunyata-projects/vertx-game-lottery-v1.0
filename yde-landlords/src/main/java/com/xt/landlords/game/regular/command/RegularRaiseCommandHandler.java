package com.xt.landlords.game.regular.command;

import com.xt.landlords.*;
import com.xt.landlords.game.phase.BetPhaseData;
import com.xt.landlords.game.phase.BetPhaseModel;
import com.xt.landlords.game.regular.GameRegularEvent;
import com.xt.landlords.game.regular.GameRegularModel;
import com.xt.landlords.game.regular.GameRegularState;
import com.xt.landlords.statemachine.GameController;
import com.xt.yde.protobuf.common.Common;
import com.xt.yde.protobuf.regular.GameRegular;
import com.xt.yde.thrift.regular.RegularCardsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.ImmutableState;
import org.sunyata.octopus.OctopusRequest;
import org.sunyata.octopus.OctopusResponse;
import org.sunyata.spring.thrift.client.annotation.ThriftClient;

/**
 * Created by leo on 17/5/15.
 */
@Component(Commands.RegularRaise)
public class RegularRaiseCommandHandler extends AbstractGameControllerCommandHandler {
    class CommandErrorCode {

    }

    Logger logger = LoggerFactory.getLogger(RegularRaiseCommandHandler.class);
    @ThriftClient(serviceId = "yde-card-service", path = "/regular")
    RegularCardsService.Client cardService;

    @Override
    public void execute(OctopusRequest request, OctopusResponse response) throws Exception {
        try {
            if (!this.canAccpet(request, GameRegularEvent.Raise)) {
                response.setErrorCode(CommonCommandErrorCode.CanNotAcceptEventException);
                return;
            }
            GameController gameController = GameManager.getGameController(request.getSession().getCurrentUser()
                    .getName());
            if (gameController == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }
            GameRegularModel gameModel = (GameRegularModel) gameController.getGameModel();// storeManager

            if (gameModel == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }

            GameRegular.RegularRaiseBetRequestMsg raiseBetRequestMsg = GameRegular.RegularRaiseBetRequestMsg
                    .parseFrom(request.getMessage().getBody());

            BetPhaseModel phase = (BetPhaseModel) gameModel.getPhase(GameRegularState.Bet.getValue());
            BetPhaseData phaseData = phase.getPhaseData();
            int times = raiseBetRequestMsg.getTimes();//加注倍数
            phaseData.getBetAmt();

            gameModel.addRaisePhase(gameModel.getGameInstanceId(), times, phaseData.getBetAmt());
            Common.DealResponseMsg.Builder builder = Common.DealResponseMsg.newBuilder();
            gameController.fire(GameRegularEvent.Raise, gameModel);
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

