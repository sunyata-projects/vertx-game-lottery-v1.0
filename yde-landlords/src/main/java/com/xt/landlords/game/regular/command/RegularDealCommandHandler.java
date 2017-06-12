package com.xt.landlords.game.regular.command;

import com.xt.landlords.*;
import com.xt.landlords.game.phase.*;
import com.xt.landlords.game.regular.GameRegularEvent;
import com.xt.landlords.game.regular.GameRegularModel;
import com.xt.landlords.game.regular.GameRegularState;
import com.xt.landlords.statemachine.GameController;
import com.xt.yde.protobuf.common.Common;
import com.xt.yde.thrift.regular.RegularCards;
import com.xt.yde.thrift.regular.RegularCardsService;
import info.developerblog.spring.thrift.annotation.ThriftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.ImmutableState;
import org.sunyata.octopus.OctopusRequest;
import org.sunyata.octopus.OctopusResponse;

/**
 * Created by leo on 17/5/15.
 */
@Component(Commands.RegularDeal)
public class RegularDealCommandHandler extends AbstractGameControllerCommandHandler {
    class CommandErrorCode {

    }

    Logger logger = LoggerFactory.getLogger(RegularDealCommandHandler.class);
    @ThriftClient(serviceId = "yde-card-service", path = "/regular")
    RegularCardsService.Client cardService;

    @Override
    public void execute(OctopusRequest request, OctopusResponse response) throws Exception {
        try {
            boolean isDeal = false;
            if (this.canAccpet(request, GameRegularEvent.Deal)) {
                isDeal = true;
            } else if (this.canAccpet(request, GameRegularEvent.Dark)) {
                isDeal = false;
            } else {
                response.setErrorCode(CommonCommandErrorCode.CanNotAcceptEventException);
                return;
            }
            GameController gameController = GameManager.getGameController(request.getSession().getCurrentUser()
                    .getName());
            if (gameController == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }
            GameRegularModel gameModel = (GameRegularModel) gameController.getGameModel();// storeManager
            // .getGameModelFromCache
            // (request.getSession().getCurrentUser().getName());

            if (gameModel == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }


            BetPhaseModel phase = (BetPhaseModel) gameModel.getPhase(GameRegularState.Bet.getValue());
            BetPhaseData phaseData = phase.getPhaseData();
            TicketResult ticketResult = phaseData.getTicketResult();
            int prizeLevel = ticketResult.getPrizeLevel();
            Common.DealResponseMsg.Builder builder = Common.DealResponseMsg.newBuilder();
            if (isDeal) {
                RegularCards cards17 = cardService.getCards17();
//                DealPhaseModel dealPhaseModel = new DealPhaseModel();
//                DealPhaseData dealPhaseData = new DealPhaseData();
//                dealPhaseData.setCardId(cards17.getCardId());
//                dealPhaseData.setCenterCard(cards17.getCenter());
//                dealPhaseModel.setPhaseData(dealPhaseData);
//                gameModel.addOrUpdatePhase(dealPhaseModel);
                gameModel.addDealPhase(cards17.getCardId(), cards17.getCenter());
                builder.addAllCenterCard(cards17.getCenter());
            } else {
                DealPhaseModel dealPhase = (DealPhaseModel) gameModel.getPhase(GameRegularState.Deal.getValue());
                DealPhaseData dealPhaseData = dealPhase.getPhaseData();
                RegularCards cards37 = cardService.getCards37(prizeLevel, dealPhaseData.getCardId());

//                DealPhaseModel darkPhaseModel = new DealPhaseModel();
//                DealPhaseData darkPhaseData = new DealPhaseData();
//                darkPhaseData
//                        .setCardId(cards37.getCardId())
//                        .setCenterCard(cards37.getCenter())
//                        .setLeftCard(cards37.getLeft())
//                        .setRightCard(cards37.getRight())
//                        .setDarkCard(cards37.getUnder());
//                darkPhaseModel.setPhaseData(darkPhaseData);
//                gameModel.addOrUpdatePhase(darkPhaseModel);
                gameModel.addDarkPhase(cards37.getCardId(), cards37.getCenter(), cards37.getRight(), cards37
                                .getLeft(),
                        cards37.getUnder());
                builder.addAllDarkCard(cards37.getUnder()).addAllCenterCard(cards37.getCenter())
                        .addAllRightCard(cards37.getRight()).addAllLeftCard(cards37.getLeft());
            }

            if (isDeal) {
                gameController.fire(GameRegularEvent.Deal, gameModel);
            } else {
                gameController.fire(GameRegularEvent.Dark, gameModel);
            }
            //List<List<Integer>> cardList = cards.getCards();
            ImmutableState currentRawState = gameController.getCurrentRawState();
            logger.info("{}:currentState:{}", this.getClass().getName(), currentRawState);
            response.setBody(builder.build().toByteArray());
        } catch (Exception ex) {
            ExceptionProcessor.process(response, ex);
        } finally {
            response.writeAndFlush();
        }
    }

    @Override
    public boolean isAsync() {
        return false;
    }
}

