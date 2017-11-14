package com.xt.landlords.game.classic.command;

import com.xt.landlords.*;
import com.xt.landlords.game.classic.GameClassicEvent;
import com.xt.landlords.game.classic.GameClassicModel;
import com.xt.landlords.game.classic.GameClassicState;
import com.xt.landlords.game.phase.BetPhaseData;
import com.xt.landlords.statemachine.GameController;
import com.xt.yde.protobuf.common.Common;
import com.xt.yde.thrift.card.classic.ClassicCards;
import com.xt.yde.thrift.card.classic.ClassicCardsService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.ImmutableState;
import org.sunyata.octopus.OctopusRequest;
import org.sunyata.octopus.OctopusResponse;
import org.sunyata.octopus.model.GamePhaseModel;
import org.sunyata.spring.thrift.client.annotation.ThriftClient;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

//import com.xt.landlords.game.puzzle.GamePuzzlePhaseName;

/**
 * Created by leo on 17/5/15.
 */
@Component(Commands.ClassicDeal)
public class ClassicDealCommandHandler extends AbstractGameControllerCommandHandler {
    org.slf4j.Logger logger = LoggerFactory.getLogger(ClassicDealCommandHandler.class);

    class CommandErrorCode {

    }

    @ThriftClient(serviceId = "yde-card-service", path = "/classic")
    ClassicCardsService.Client cardService;
    Random random = new Random();
    @Autowired
    StoreManager storeManager;

    public int nextInt(int from, int to) {
        int max = to;
        int min = from;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    @Override
    public void execute(OctopusRequest request, OctopusResponse response) throws Exception {
        try {
            if (!this.canAccpet(request, GameClassicEvent.Deal)) {
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
            GamePhaseModel phase = gameModel.getPhase(GameClassicState.Bet.getValue());
            BetPhaseData phaseData = (BetPhaseData) phase.getPhaseData();
            Common.DealResponseMsg.Builder builder = Common.DealResponseMsg.newBuilder();
            ClassicCards cards = cardService.getCards((int) phaseData.getTicketResult().getPrizeLevel());

            if (Objects.equals((int)phaseData.getTicketResult().getPrizeLevel() , 6)) {
                builder.setIsZhiZun(true);
            }
            builder.addAllCenterCard(cards.getCenter());
            builder.addAllRightCard(cards.getRight());
            builder.addAllLeftCard(cards.getLeft());
            builder.addAllDarkCard(cards.getUnder());

            gameModel.addDealPhase(cards);
            gameController.fire(GameClassicEvent.Deal, gameModel);
            ImmutableState currentRawState = gameController.getCurrentRawState();
            logger.info("{}:currentState:{}", this.getClass().getName(), currentRawState);
            response.setBody(builder.build().toByteArray());
        } catch (Exception ex) {
            ExceptionProcessor.process(response, ex);
        } finally {
            response.writeAndFlush();
        }
    }

    public int getMoney(int grade, int betAmt) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(1, 10000);
        map.put(2, 1000);
        map.put(3, 200);
        map.put(4, 100);
        map.put(5, 50);
        map.put(6, 10);
        map.put(7, 5);
        map.put(8, 2);
        map.put(9, 1);
        map.put(0, 0);
        return map.get(grade) * betAmt;
    }

    @Override
    public boolean isAsync() {
        return false;
    }
}

