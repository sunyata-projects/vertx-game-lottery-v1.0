package com.xt.landlords.game.puzzle.command;

import com.xt.landlords.*;
import com.xt.landlords.game.phase.BetPhaseData;
import com.xt.landlords.game.puzzle.GamePuzzleEvent;
import com.xt.landlords.game.puzzle.GamePuzzleModel;
import com.xt.landlords.game.puzzle.GamePuzzleState;
import com.xt.landlords.statemachine.GameController;
import com.xt.yde.protobuf.puzzle.GamePuzzle;
import com.xt.yde.thrift.card.puzzle.PuzzleCards;
import com.xt.yde.thrift.card.puzzle.PuzzleCardsService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.ImmutableState;
import org.sunyata.octopus.OctopusRequest;
import org.sunyata.octopus.OctopusResponse;
import org.sunyata.octopus.model.GamePhaseModel;
import org.sunyata.spring.thrift.client.annotation.ThriftClient;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

//import com.xt.landlords.game.puzzle.GamePuzzlePhaseName;

/**
 * Created by leo on 17/5/15.
 */
@Component(Commands.PuzzleDeal)
public class PuzzleDealCommandHandler extends AbstractGameControllerCommandHandler {
    org.slf4j.Logger logger = LoggerFactory.getLogger(PuzzleDealCommandHandler.class);

    class CommandErrorCode {

    }

    @ThriftClient(serviceId = "yde-card-service", path = "/puzzle")
    PuzzleCardsService.Client cardService;
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
            if (!this.canAccpet(request, GamePuzzleEvent.Deal)) {
                response.setErrorCode(CommonCommandErrorCode.CanNotAcceptEventException);
                return;
            }
            GameController gameController = GameManager.getGameController(request.getSession().getCurrentUser()
                    .getName());
            if (gameController == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }
            GamePuzzleModel gameModel = (GamePuzzleModel) gameController.getGameModel();// storeManager
            // .getGameModelFromCache
            // (request.getSession().getCurrentUser().getName());

            if (gameModel == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }
            GamePuzzle.DealResponseMsg.Builder builder = GamePuzzle.DealResponseMsg.newBuilder();
//            int grade = random.nextInt(9) + 1;
            //int grade = nextInt(0, 9);
            int grade = 0;
            if( nextInt(1,2)==2){
                grade = 1;
            }
//            grade = 0;
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
            GamePhaseModel phase = gameModel.getPhase(GamePuzzleState.Bet.getValue());
            BetPhaseData phaseData = (BetPhaseData) phase.getPhaseData();
            int money = getMoney(grade, phaseData.getBetAmt());
            builder.addTotalMoney(money);
            gameModel.addDealPhase("cardId", money);
            gameController.fire(GamePuzzleEvent.Deal, gameModel);
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

