package com.xt.landlords.game.classic.command;

import com.xt.landlords.*;
import com.xt.landlords.game.classic.GameClassicEvent;
import com.xt.landlords.game.classic.GameClassicModel;
import com.xt.landlords.game.classic.GameClassicState;
import com.xt.landlords.game.phase.BetPhaseData;
import com.xt.landlords.game.phase.TicketResult;
import com.xt.landlords.service.ClassicPrizeManager;
import com.xt.landlords.statemachine.GameController;
import com.xt.yde.protobuf.classic.GameClassic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.ImmutableState;
import org.sunyata.octopus.OctopusRequest;
import org.sunyata.octopus.OctopusResponse;

import java.util.Random;

/**
 * Created by leo on 17/5/15.
 */
@Component(Commands.ClassicSummaryClear)
public class ClassicSummaryCommandHandler extends AbstractGameControllerCommandHandler {
    class CommandErrorCode {

    }

    Logger logger = LoggerFactory.getLogger(ClassicSummaryCommandHandler.class);

    @Autowired
    ClassicPrizeManager ClassicPrizeManager;

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
            GameClassicModel gameModel = (GameClassicModel) gameController.getGameModel();// storeManager

            if (gameModel == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }

            GameClassic.SummaryClearGameResponseMsg.Builder builder = GameClassic.SummaryClearGameResponseMsg
                    .newBuilder();

            BetPhaseData betPhaseData = (BetPhaseData) gameController.getPhaseData(GameClassicState.Bet.getValue());
            TicketResult ticketResult = betPhaseData.getTicketResult();
            builder.setMoney(ticketResult.getPrizeCash().toPlainString());
            ImmutableState currentRawState = gameController.getCurrentRawState();
            logger.info("{}:currentState:{}", this.getClass().getName(), currentRawState);
            response.setBody(builder.build().toByteArray());
        } catch (Exception ex) {
            ExceptionProcessor.process(response, ex);
        } finally {
            response.writeAndFlush();
        }
    }


    Random random = new Random();

    public int nextInt(int from, int to) {
        int max = to;
        int min = from;
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

//    private int getTimes(GameModel gameModel) {
//        BetPhaseModel phase = (BetPhaseModel) gameModel.getPhase(GameClassicState.Bet.getValue());
//        BetPhaseData phaseData = phase.getPhaseData();
//        TicketResult ticketResult = phaseData.getTicketResult();
//        int prizeLevel = ticketResult.getPrizeLevel();
//        return prizeLevel;
//    }

    @Override
    public boolean isAsync() {
        return false;
    }
}

