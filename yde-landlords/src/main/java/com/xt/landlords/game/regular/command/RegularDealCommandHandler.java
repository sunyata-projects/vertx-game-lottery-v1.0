package com.xt.landlords.game.regular.command;

import com.xt.landlords.*;
import com.xt.landlords.game.phase.*;
import com.xt.landlords.game.regular.GameRegularEvent;
import com.xt.landlords.game.regular.GameRegularModel;
import com.xt.landlords.game.regular.GameRegularState;
import com.xt.landlords.statemachine.GameController;
import com.xt.yde.protobuf.common.Common;
import com.xt.yde.thrift.card.regular.RegularCards;
import com.xt.yde.thrift.card.regular.RegularCardsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.ImmutableState;
import org.sunyata.octopus.OctopusRequest;
import org.sunyata.octopus.OctopusResponse;
import org.sunyata.spring.thrift.client.annotation.ThriftClient;

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
            int prizeLevel = (int) ticketResult.getPrizeLevel();
            Common.DealResponseMsg.Builder builder = Common.DealResponseMsg.newBuilder();
            if (isDeal) {
                RegularCards cards17 = cardService.getCards17();
                gameModel.addDealPhase(cards17.getCardId(), cards17.getCenter());
                builder.addAllCenterCard(cards17.getCenter());
            } else {
                DealPhaseModel dealPhase = (DealPhaseModel) gameModel.getPhase(GameRegularState.Deal.getValue());
                DealPhaseData dealPhaseData = dealPhase.getPhaseData();
                RegularCards cards37 = cardService.getCards37(prizeLevel, dealPhaseData.getCardId());
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

