package com.xt.landlords.game.eliminate.command;

import com.xt.landlords.*;
import com.xt.landlords.game.eliminate.GameEliminateEvent;
import com.xt.landlords.game.eliminate.GameEliminateModel;
import com.xt.landlords.game.eliminate.GameEliminateState;
import com.xt.landlords.game.eliminate.phase.EliminatePlayPhaseData;
import com.xt.landlords.game.eliminate.phase.EliminatePlayPhaseDataItem;
import com.xt.landlords.game.eliminate.phase.EliminatePlayPhaseModel;
import com.xt.landlords.statemachine.GameController;
import com.xt.yde.protobuf.eliminate.Eliminate;
import com.xt.yde.thrift.card.eliminate.EliminateCards;
import com.xt.yde.thrift.card.eliminate.EliminateCardsService;
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
@Component(Commands.EliminateDeal)
public class EliminateDealCommandHandler extends AbstractGameControllerCommandHandler {
    org.slf4j.Logger logger = LoggerFactory.getLogger(EliminateDealCommandHandler.class);

    class CommandErrorCode {

    }

    @ThriftClient(serviceId = "yde-card-service", path = "/eliminate")
    EliminateCardsService.Client cardService;
    Random random = new Random();
    @Autowired
    StoreManager storeManager;

    @Override
    public void execute(OctopusRequest request, OctopusResponse response) throws Exception {
        try {
            if (!this.canAccpet(request, GameEliminateEvent.Deal)) {
                response.setErrorCode(CommonCommandErrorCode.CanNotAcceptEventException);
                return;
            }
            GameController gameController = GameManager.getGameController(request.getSession().getCurrentUser()
                    .getName());
            if (gameController == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }
            GameEliminateModel gameModel = (GameEliminateModel) gameController.getGameModel();// storeManager
            // .getGameModelFromCache
            // (request.getSession().getCurrentUser().getName());

            if (gameModel == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }
            if (gameModel.isCompleteAwardCondition()) {
                response.setErrorCode(CommonCommandErrorCode.CanNotAcceptEventException);
                return;
            }
            EliminatePlayPhaseModel phase = (EliminatePlayPhaseModel) gameModel.getPhase(GameEliminateState.Play
                    .getValue());
            EliminatePlayPhaseData phaseData = phase.getPhaseData();
            EliminatePlayPhaseDataItem lastPlayPhaseDataItem = phaseData.getLastPlayPhaseDataItem();

            EliminateCards cards = null;
            cards = cardService.getCards(lastPlayPhaseDataItem.getAwardLevel(), lastPlayPhaseDataItem
                    .getDoubleKingCount());
            lastPlayPhaseDataItem.setCardId(cards.getCardId()).setCards(cards.getCards());


            gameController.fire(GameEliminateEvent.Deal, gameModel);

            Eliminate.EliminateDealResponseMsg.Builder builder = Eliminate.EliminateDealResponseMsg.newBuilder();
            builder.setZhiZun(lastPlayPhaseDataItem.getAwardLevel() == 99);
            List<List<Integer>> cardList = cards.getCards();
            for (List<Integer> cardRow : cardList) {
                Eliminate.CardRow.Builder builderCardRow = Eliminate.CardRow.newBuilder();
                builderCardRow.addAllCards(cardRow);
                builder.addCards(builderCardRow.build());
            }
            builder
                    .setTotalAwardGamePoint(lastPlayPhaseDataItem.getTotalAwardGamePoint())
                    .setGameProgress(lastPlayPhaseDataItem.getTotalDoubleKingCount())
                    .setExchangeGamePointBalance(lastPlayPhaseDataItem.getExchangeGamePointBalance());
            ImmutableState currentRawState = gameController.getCurrentRawState();
            logger.info("{}:currentState:{}", this.getClass().getName(), currentRawState);
            response.setBody(builder.build().toByteArray());
        } catch (Exception ex) {
            ExceptionProcessor.process(response, ex);
        } finally {
            response.writeAndFlush();
        }
    }

    public int getMoney(int grade) {
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
        return map.get(grade);
    }

    @Override
    public boolean isAsync() {
        return false;
    }
}

