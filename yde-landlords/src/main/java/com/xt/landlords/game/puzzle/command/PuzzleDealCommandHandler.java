package com.xt.landlords.game.puzzle.command;

import com.xt.landlords.*;
import com.xt.landlords.game.puzzle.GamePuzzleEvent;
//import com.xt.landlords.game.puzzle.GamePuzzlePhaseName;
import com.xt.landlords.game.puzzle.GamePuzzleState;
import com.xt.landlords.game.puzzle.phase.PuzzleDealPhaseData;
import com.xt.landlords.game.puzzle.phase.PuzzleDealPhaseModel;
import com.xt.landlords.statemachine.GameController;
import com.xt.yde.protobuf.puzzle.GamePuzzle;
import com.xt.yde.thrift.card.puzzle.PuzzleCards;
import com.xt.yde.thrift.card.puzzle.PuzzleCardsService;
import info.developerblog.spring.thrift.annotation.ThriftClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.octopus.OctopusRequest;
import org.sunyata.octopus.OctopusResponse;
import org.sunyata.octopus.model.GameModel;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by leo on 17/5/15.
 */
@Component(Commands.PuzzleDeal)
public class PuzzleDealCommandHandler extends AbstractGameControllerCommandHandler {
    class CommandErrorCode {

    }

    @ThriftClient(serviceId = "yde-card-service", path = "/puzzle")
    PuzzleCardsService.Client cardService;
    Random random = new Random();
    @Autowired
    StoreManager storeManager;

    @Override
    public void execute(OctopusRequest request, OctopusResponse response) throws Exception {
        try {
            if (!this.canAccpet(request, GamePuzzleEvent.Deal)) {
                response.setErrorCode(CommonCommandErrorCode.CanNotAcceptEventException);
                return;
            }
            GameController gameController = GameManager.getGameController(request.getSession().getCurrentUser()
                    .getName());
            GameModel gameModel = storeManager.getGameModelFromCache(request.getSession().getCurrentUser()
                    .getName());
            if (gameController == null || gameModel == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }

            GamePuzzle.DealResponseMsg.Builder builder = GamePuzzle.DealResponseMsg.newBuilder();
            int grade = random.nextInt(9) + 1;
            PuzzleCards cards = cardService.getCards(grade);

            List<List<List<Integer>>> cardList = cards.getCards();
            for (List<List<Integer>> listList : cardList) {
                GamePuzzle.CardFourRow.Builder cardFourRowBuilder = GamePuzzle.CardFourRow.newBuilder();
                for (List<Integer> list : listList) {
                    GamePuzzle.CardRow.Builder cardRowBuilder = GamePuzzle.CardRow.newBuilder();
                    cardRowBuilder.addAllCards(list);
                    cardFourRowBuilder.addCards(cardRowBuilder.build());
                }
                GamePuzzle.CardFourRow cardFourRow = cardFourRowBuilder.build();
                builder.addCards(cardFourRow);
            }
            int money = getMoney(grade);
            builder.addTotalMoney(money);
            PuzzleDealPhaseData phaseData = new PuzzleDealPhaseData().setTotalMoney(money);
            PuzzleDealPhaseModel phaseModel = new PuzzleDealPhaseModel(gameModel.getGameInstanceId(),
                    GamePuzzleState.Deal.getValue(), 2);
            gameModel.addPhase(phaseModel.setPhaseData(phaseData));
            gameController.fire(GamePuzzleEvent.Deal, gameModel);
            response.setBody(builder.build().toByteArray());
        } catch (Exception ex) {

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

