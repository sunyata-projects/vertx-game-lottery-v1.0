package com.xt.landlords.game.rank.command;

import com.xt.landlords.*;
import com.xt.landlords.game.phase.DealPhaseModel;
import com.xt.landlords.game.phase.TicketResult;
import com.xt.landlords.game.rank.GameRankEvent;
import com.xt.landlords.game.rank.GameRankModel;
import com.xt.landlords.game.rank.phase.RankPlayPhaseModel;
import com.xt.landlords.statemachine.GameController;
import com.xt.yde.protobuf.common.Common;
import com.xt.yde.thrift.card.rank.RankCards;
import com.xt.yde.thrift.card.rank.RankCardsService;
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
@Component(Commands.RankDeal)
public class RankDealCommandHandler extends AbstractGameControllerCommandHandler {
    class CommandErrorCode {

    }

    Logger logger = LoggerFactory.getLogger(RankDealCommandHandler.class);
    @ThriftClient(serviceId = "yde-card-service", path = "/rank")
    RankCardsService.Client cardService;

    @Override
    public void execute(OctopusRequest request, OctopusResponse response) throws Exception {
        try {
            if (!this.canAccpet(request, GameRankEvent.Deal)) {
                response.setErrorCode(CommonCommandErrorCode.CanNotAcceptEventException);
                return;
            }
            GameController gameController = GameManager.getGameController(request.getSession().getCurrentUser()
                    .getName());
            if (gameController == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }
            GameRankModel gameModel = (GameRankModel) gameController.getGameModel();// storeManager
            // .getGameModelFromCache
            // (request.getSession().getCurrentUser().getName());

            if (gameModel == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }


            Common.DealResponseMsg.Builder builder = Common.DealResponseMsg.newBuilder();
            RankPlayPhaseModel rankPlayPhaseModel = gameModel.getLastPlayPhaseModel();
            int rankIndex = rankPlayPhaseModel == null ? 0 : rankPlayPhaseModel.getRankIndex();
            //long rankIndex = rankPlayPhaseModel.getRankIndex();
            TicketResult ticketResult = gameModel.getPrizeLevel((int) rankIndex);
            //boolean isLose = getIsLose(times, RankIndex);
            DealPhaseModel dealPhaseModel = gameModel.addDealPhase();
            RankCards cards = cardService.getCards((int) ticketResult.getPrizeLevel());
            dealPhaseModel.getPhaseData().setCenterCard(cards.getCenter()).setLeftCard(cards.getLeft()).setRightCard
                    (cards.getRight()).setDarkCard(cards.getUnder()).setCardId(cards.getCardId());
            //RankPlayPhaseData phaseData = rankPlayPhaseModel.getPhaseData();

            builder.addAllDarkCard(cards.getUnder()).addAllCenterCard(cards.getCenter())
                    .addAllRightCard(cards.getRight()).addAllLeftCard(cards.getLeft());
            //phaseData.addDealItem(cards);

            gameController.fire(GameRankEvent.Deal, gameModel);
            gameModel.addPlayPhase();
            ImmutableState currentRawState = gameController.getCurrentRawState();
            logger.info("{}:currentState:{}", this.getClass().getName(), currentRawState);
            response.setBody(builder.build().toByteArray());
        } catch (Exception ex) {
            ExceptionProcessor.process(response, ex);
        } finally {
            response.writeAndFlush();
        }
    }

    boolean getIsLose(float times, long RankIndex) {
        HashMap<Float, Integer> maps = new HashMap<>();
        //maps.put(0.0f, 0);
        maps.put(1.5f, 2);
        maps.put(3f, 3);
        maps.put(6f, 4);
        //maps.put(50f, 5);
        if (times == 0f) {
            if (RankIndex == 2) {
                return true;
            } else if (RankIndex == 1) {
                int i = nextInt(0, 1);
                return i == 0;
            } else {
                return true;
            }
        } else if (times < 50f) {
            Integer winRank = maps.getOrDefault(times, 0);
            return RankIndex > winRank;
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
//        BetPhaseModel phase = (BetPhaseModel) gameModel.getPhase(GameRankState.Bet.getValue());
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

