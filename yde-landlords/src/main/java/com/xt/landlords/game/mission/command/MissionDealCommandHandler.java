package com.xt.landlords.game.mission.command;

import com.xt.landlords.*;
import com.xt.landlords.game.mission.GameMissionEvent;
import com.xt.landlords.game.mission.GameMissionModel;
import com.xt.landlords.game.mission.phase.MissionPlayPhaseData;
import com.xt.landlords.game.mission.phase.MissionPlayPhaseModel;
import com.xt.landlords.statemachine.GameController;
import com.xt.yde.protobuf.common.Common;
import com.xt.yde.thrift.card.mission.MissionCards;
import com.xt.yde.thrift.card.mission.MissionCardsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Component(Commands.MissionDeal)
public class MissionDealCommandHandler extends AbstractGameControllerCommandHandler {
    class CommandErrorCode {

    }

    Logger logger = LoggerFactory.getLogger(MissionDealCommandHandler.class);
    @ThriftClient(serviceId = "yde-card-service", path = "/mission")
    MissionCardsService.Client cardService;

    @Override
    public void execute(OctopusRequest request, OctopusResponse response) throws Exception {
        try {
            if (!this.canAccpet(request, GameMissionEvent.Deal)) {
                response.setErrorCode(CommonCommandErrorCode.CanNotAcceptEventException);
                return;
            }
            GameController gameController = GameManager.getGameController(request.getSession().getCurrentUser()
                    .getName());
            if (gameController == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }
            GameMissionModel gameModel = (GameMissionModel) gameController.getGameModel();// storeManager
            // .getGameModelFromCache
            // (request.getSession().getCurrentUser().getName());

            if (gameModel == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }


            Common.DealResponseMsg.Builder builder = Common.DealResponseMsg.newBuilder();
            MissionPlayPhaseModel missionPlayPhaseModel = gameModel.addPlayPhase();

            float times = gameModel.getTimes();
            long missionIndex = missionPlayPhaseModel.getMissionIndex();
            boolean isLose = getIsLose(times, missionIndex);
            MissionCards cards = cardService.getCards(isLose, (int) missionIndex);

            MissionPlayPhaseData phaseData = missionPlayPhaseModel.getPhaseData();
            builder.addAllDarkCard(cards.getUnder()).addAllCenterCard(cards.getCenter())
                    .addAllRightCard(cards.getRight()).addAllLeftCard(cards.getLeft());
            phaseData.addDealItem(cards);
            gameController.fire(GameMissionEvent.Deal, gameModel);
            ImmutableState currentRawState = gameController.getCurrentRawState();
            logger.info("{}:currentState:{}", this.getClass().getName(), currentRawState);
            response.setBody(builder.build().toByteArray());
        } catch (Exception ex) {
            ExceptionProcessor.process(response, ex);
        } finally {
            response.writeAndFlush();
        }
    }

    boolean getIsLose(float times, long missionIndex) {
        HashMap<Float, Integer> maps = new HashMap<>();
        //maps.put(0.0f, 0);
        maps.put(1.5f, 2);
        maps.put(3f, 3);
        maps.put(6f, 4);
        //maps.put(50f, 5);
        if (times == 0f) {
            if (missionIndex == 2) {
                return true;
            } else if (missionIndex == 1) {
                int i = nextInt(0, 1);
                return i == 0;
            } else {
                return true;
            }
        } else if (times < 50f) {
            Integer winMission = maps.getOrDefault(times, 0);
            return missionIndex > winMission;
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
//        BetPhaseModel phase = (BetPhaseModel) gameModel.getPhase(GameMissionState.Bet.getValue());
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

