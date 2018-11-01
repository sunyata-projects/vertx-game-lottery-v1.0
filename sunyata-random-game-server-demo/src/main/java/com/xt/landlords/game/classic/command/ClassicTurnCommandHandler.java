package com.xt.landlords.game.classic.command;

import com.xt.landlords.*;
import com.xt.landlords.game.classic.GameClassicEvent;
import com.xt.landlords.game.classic.GameClassicModel;
import com.xt.landlords.game.classic.GameClassicState;
import com.xt.landlords.game.classic.phase.ClassicPlayPhaseData;
import com.xt.landlords.game.classic.phase.ClassicTurnPhaseData;
import com.xt.landlords.game.classic.phase.ClassicTurnPhaseModel;
import com.xt.landlords.game.phase.BetPhaseData;
import com.xt.landlords.game.phase.TicketResult;
import com.xt.landlords.service.ClassicPrizeManager;
import com.xt.landlords.service.TurnResultInfo;
import com.xt.landlords.statemachine.GameController;
import com.xt.yde.protobuf.common.Common;
import com.xt.yde.thrift.card.classic.ClassicCardsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.ImmutableState;
import org.sunyata.octopus.OctopusRequest;
import org.sunyata.octopus.OctopusResponse;
import org.sunyata.spring.thrift.client.annotation.ThriftClient;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by leo on 17/5/15.
 */
@Component(Commands.ClassicTurn)
public class ClassicTurnCommandHandler extends AbstractGameControllerCommandHandler {
    class CommandErrorCode {

    }

    Logger logger = LoggerFactory.getLogger(ClassicTurnCommandHandler.class);
    @ThriftClient(serviceId = "yde-card-service", path = "/Classic")
    ClassicCardsService.Client cardService;

    @Autowired
    ClassicPrizeManager ClassicPrizeManager;

    @Override
    public void execute(OctopusRequest request, OctopusResponse response) throws Exception {
        try {
            if (!this.canAccpet(request, GameClassicEvent.Turn)) {
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

            Common.TurntableResponseMsg.Builder builder = Common.TurntableResponseMsg.newBuilder();
            //int ClassicIndex = ((GameClassicModel) gameController.getGameModel()).getLastPlayPhaseModel()
              //      .getClassicIndex();

            BetPhaseData betPhaseData = (BetPhaseData) gameController.getPhaseData(GameClassicState.Bet.getValue());
            TicketResult ticketResult = betPhaseData.getTicketResult();
            ClassicPlayPhaseData playPhaseData = gameModel.getLastPlayPhaseModel().getPhaseData();
            Integer currentBombNumbers = playPhaseData.getCurrentBombNumbers();
            TurnResultInfo turnResultInfo = null;
            turnResultInfo = ClassicPrizeManager.getTurnMoneyAndScore(betPhaseData.getBetAmt(), ticketResult
                    .getPrizeLevel(),
                    playPhaseData
                    .isWin(), currentBombNumbers);
            ClassicTurnPhaseModel ClassicPlayPhaseModel = gameModel.addTurnPhase();
            ClassicTurnPhaseData phaseData = ClassicPlayPhaseModel.getPhaseData();
            phaseData.setMoney(turnResultInfo.getMoney());
            gameController.fire(GameClassicEvent.Turn, gameModel);
//            if (gameModel.getIsOver()) {
//                gameModel.addClearPhase();
//                gameController.fire(GameClassicEvent.GameOver, gameModel);
//            }
            builder.setMoney(turnResultInfo.getMoney().toPlainString());
            ImmutableState currentRawState = gameController.getCurrentRawState();
            logger.info("{}:currentState:{}", this.getClass().getName(), currentRawState);
            response.setBody(builder.build().toByteArray());
        } catch (Exception ex) {
            ExceptionProcessor.process(response, ex);
        } finally {
            response.writeAndFlush();
        }
    }

    boolean getIsLose(float times, long ClassicIndex) {
        HashMap<Float, Integer> maps = new HashMap<>();
        //maps.put(0.0f, 0);
        maps.put(1.5f, 2);
        maps.put(3f, 3);
        maps.put(6f, 4);
        //maps.put(50f, 5);
        if (times == 0f) {
            if (ClassicIndex == 2) {
                return true;
            } else if (ClassicIndex == 1) {
                int i = nextInt(0, 1);
                return i == 0;
            } else {
                return true;
            }
        } else if (times < 50f) {
            Integer winClassic = maps.getOrDefault(times, 0);
            return ClassicIndex > winClassic;
        } else {
            return times < 50;
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

