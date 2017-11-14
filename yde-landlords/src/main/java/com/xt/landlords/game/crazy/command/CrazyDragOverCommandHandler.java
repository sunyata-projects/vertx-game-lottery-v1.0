package com.xt.landlords.game.crazy.command;

import com.xt.landlords.*;
import com.xt.landlords.game.crazy.GameCrazyEvent;
import com.xt.landlords.game.crazy.GameCrazyModel;
import com.xt.landlords.game.crazy.GameCrazyState;
import com.xt.landlords.game.crazy.phase.CrazyDealPhaseModel;
import com.xt.landlords.game.phase.BetPhaseModel;
import com.xt.landlords.statemachine.GameController;
import com.xt.yde.protobuf.crazy.Crazy;
import com.xt.yde.thrift.card.crazy.CrazyCardsService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.ImmutableState;
import org.sunyata.octopus.OctopusRequest;
import org.sunyata.octopus.OctopusResponse;
import org.sunyata.spring.thrift.client.annotation.ThriftClient;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

//import com.xt.landlords.game.puzzle.GamePuzzlePhaseName;

/**
 * Created by leo on 17/5/15.
 */
@Component(Commands.CrazyDragOver)
public class CrazyDragOverCommandHandler extends AbstractGameControllerCommandHandler {
    org.slf4j.Logger logger = LoggerFactory.getLogger(CrazyDragOverCommandHandler.class);

    class CommandErrorCode {

    }

    @ThriftClient(serviceId = "yde-card-service", path = "/crazy")
    CrazyCardsService.Client cardService;
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
            if (!this.canAccpet(request, GameCrazyEvent.DragOver)) {
                response.setErrorCode(CommonCommandErrorCode.CanNotAcceptEventException);
                return;
            }
            GameController gameController = GameManager.getGameController(request.getSession().getCurrentUser()
                    .getName());
            if (gameController == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }
            GameCrazyModel gameModel = (GameCrazyModel) gameController.getGameModel();// storeManager
            if (gameModel == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }


            Crazy.CrazyDragCardOverRequestMsg crazyDragCardOverRequestMsg = Crazy.CrazyDragCardOverRequestMsg.parseFrom
                    (request
                            .getMessage().getBody());
            gameController.fire(GameCrazyEvent.DragOver, gameModel);
            ImmutableState currentRawState = gameController.getCurrentRawState();
            logger.info("{}:currentState:{}", this.getClass().getName(), currentRawState);
            Crazy.CrazyDragCardOverResponseMsg.Builder builder = Crazy.CrazyDragCardOverResponseMsg.newBuilder();

            BetPhaseModel betPhaseModel = (BetPhaseModel) gameModel.getPhase(GameCrazyState.Bet.getValue());
            CrazyDealPhaseModel crazyDealPhaseModel = (CrazyDealPhaseModel) gameModel.getPhase(GameCrazyState.Deal
                    .getValue());
            BigDecimal prizeCash = betPhaseModel.getPhaseData().getTicketResult().getPrizeCash();
            Integer bombNumbers = crazyDealPhaseModel.getPhaseData().getBombNumbers();

            boolean isZhiZun = Objects.equals(crazyDealPhaseModel.getPhaseData().getCardId(), "-1");

            builder.setTotalMoney(prizeCash.toPlainString()).setBomNums(bombNumbers).setZhiZun(isZhiZun);
            //Account.addBalance(gameModel.getUserName(), prizeCash);
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

