package com.xt.landlords.game.rank.command;

import com.xt.landlords.*;
import com.xt.landlords.game.phase.BetPhaseData;
import com.xt.landlords.game.rank.GameRankEvent;
import com.xt.landlords.game.rank.GameRankModel;
import com.xt.landlords.game.rank.GameRankState;
import com.xt.landlords.game.rank.command.ranklist.RankManager;
import com.xt.landlords.service.RankPrizeManager;
import com.xt.landlords.statemachine.GameController;
import com.xt.yde.protobuf.rank.GameRank;
import com.xt.yde.thrift.card.rank.RankCardsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.ImmutableState;
import org.sunyata.octopus.OctopusRequest;
import org.sunyata.octopus.OctopusResponse;
import org.sunyata.spring.thrift.client.annotation.ThriftClient;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by leo on 17/5/15.
 */
@Component(Commands.RankClear)
public class RankClearCommandHandler extends AbstractGameControllerCommandHandler {
    class CommandErrorCode {

    }

    Logger logger = LoggerFactory.getLogger(RankClearCommandHandler.class);
    @ThriftClient(serviceId = "yde-card-service", path = "/rank")
    RankCardsService.Client cardService;

    @Autowired
    RankPrizeManager rankPrizeManager;

    @Override
    public void execute(OctopusRequest request, OctopusResponse response) throws Exception {
        try {
            if (!this.canAccpet(request, GameRankEvent.GameOver)) {
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

            if (gameModel == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }

            GameRank.RankClearGameResponseMsg.Builder builder = GameRank.RankClearGameResponseMsg.newBuilder();
//            int rankIndex = ((GameRankModel) gameController.getGameModel()).getLastPlayPhaseModel().getRankIndex();

            gameModel.addClearPhase();
            BetPhaseData betPhaseData = (BetPhaseData) gameController.getPhaseData(GameRankState.Bet.getValue());

            BigDecimal score = rankPrizeManager.getTotalScore(betPhaseData.getTicketResults());
            BigDecimal money = rankPrizeManager.getTotalMoney(betPhaseData.getTicketResults(), new BigDecimal(betPhaseData.getBetAmt()));
            builder.setMoney(money.toPlainString()).setScore(score.intValue());
            String userName = request.getSession().getCurrentUser().getName();
            RankManager.saveRoundInfo(userName, money, score);
            gameController.fire(GameRankEvent.GameOver, gameModel);
//            String userName = request.getSession().getCurrentUser().getName();
//            RankManager.saveRoundInfo(userName, money, score);
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

