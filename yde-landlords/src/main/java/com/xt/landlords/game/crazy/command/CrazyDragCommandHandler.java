package com.xt.landlords.game.crazy.command;

import com.xt.landlords.*;
import com.xt.landlords.game.crazy.GameCrazyEvent;
import com.xt.landlords.game.crazy.GameCrazyModel;
import com.xt.landlords.game.crazy.GameCrazyState;
import com.xt.landlords.game.crazy.phase.CrazyDealPhaseData;
import com.xt.landlords.game.crazy.phase.CrazyDealPhaseModel;
import com.xt.landlords.game.crazy.phase.CrazyDragPhaseModel;
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

import java.util.HashMap;
import java.util.List;
import java.util.Random;

//import com.xt.landlords.game.puzzle.GamePuzzlePhaseName;

/**
 * Created by leo on 17/5/15.
 */
@Component(Commands.CrazyDrag)
public class CrazyDragCommandHandler extends AbstractGameControllerCommandHandler {
    org.slf4j.Logger logger = LoggerFactory.getLogger(CrazyDragCommandHandler.class);

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
            if (!this.canAccpet(request, GameCrazyEvent.Drag)) {
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


            CrazyDealPhaseModel dealPhaseModel = (CrazyDealPhaseModel) gameModel.getPhase(GameCrazyState.Deal
                    .getValue());
            CrazyDealPhaseData dealPhaseData = dealPhaseModel.getPhaseData();

            Crazy.CrazyDragCardRequestMsg crazyDragCardRequestMsg = Crazy.CrazyDragCardRequestMsg.parseFrom(request
                    .getMessage().getBody());
            int divideRole = crazyDragCardRequestMsg.getDivideRole();
            CrazyDragPhaseModel dragPhaseModel = gameModel.addDragPhaseIfNecessary();
            List<Integer> drag = dragPhaseModel.drag(crazyDragCardRequestMsg.getDivideRole(), crazyDragCardRequestMsg
                            .getSelectPlaceList(),
                    crazyDragCardRequestMsg.getSendPlaceList(), dealPhaseData);

            gameController.fire(GameCrazyEvent.Drag, gameModel);
            ImmutableState currentRawState = gameController.getCurrentRawState();
            logger.info("{}:currentState:{}", this.getClass().getName(), currentRawState);
            Crazy.CrazyDragCardResponseMsg.Builder builder = Crazy.CrazyDragCardResponseMsg.newBuilder();


            builder.setDivideRole(divideRole);
//            if (divideRole == 1) {//地主
//                List<Integer> centerThreeCard = dealPhaseData.getCenterThreeCard();
//                CrazyDragPhaseData dragPhaseData = dragPhaseModel.getPhaseData();
//            } else if (divideRole == 2) {//右农民
//                builder.setRoleCards(dealPhaseData.getRightOneCard());
//
//            } else if (divideRole == 3) {//左农民
//                builder.setRoleCards(dealPhaseData.getLeftOneCard());
//            }
            for (int i = 0; i < drag.size(); i++) {
                logger.info("牌面" + String.valueOf(drag.get(i)));
            }
            builder.addAllRoleCards(drag);
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

