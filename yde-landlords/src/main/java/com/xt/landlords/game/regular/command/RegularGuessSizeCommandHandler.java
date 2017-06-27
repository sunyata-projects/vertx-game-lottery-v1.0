package com.xt.landlords.game.regular.command;

import com.xt.landlords.*;
import com.xt.landlords.game.phase.TicketResult;
import com.xt.landlords.game.regular.GameRegularEvent;
import com.xt.landlords.game.regular.GameRegularModel;
import com.xt.landlords.game.regular.GameRegularState;
import com.xt.landlords.game.regular.phase.GuessSizePhaseModel;
import com.xt.landlords.statemachine.GameController;
import com.xt.yde.protobuf.regular.GameRegular;
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
@Component(Commands.RegularGuessSize)
public class RegularGuessSizeCommandHandler extends AbstractGameControllerCommandHandler {
    class CommandErrorCode {

    }

    Logger logger = LoggerFactory.getLogger(RegularGuessSizeCommandHandler.class);
    @ThriftClient(serviceId = "yde-card-service", path = "/regular")
    RegularCardsService.Client cardService;

    @Override
    public void execute(OctopusRequest request, OctopusResponse response) throws Exception {
        try {

            if (!this.canAccpet(request, GameRegularEvent.GuessSize)) {
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

            if (gameModel == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }


//            RegularPlayPhaseModel phaseModel = (RegularPlayPhaseModel) gameModel.getPhase(GameRegularState.Playing
//                    .getValue());
//            if (phaseModel == null) {
//                response.setErrorCode(CommonCommandErrorCode.InternalError);
//                return;
//            }
//            RegularPlayPhaseData phaseData = phaseModel.getPhaseData();
//
//            //是否赢
//            boolean isWin = phaseData != null && phaseData.isIfEnd() && phaseData.isWin();
//            //牌局已经终结,赢,并且是0炸
//            boolean guessSizeFlag = isWin && (phaseData.getCurrentBombNumbers() == 0);
//
//            if (guessSizeFlag) {//可以翻牌
//                if (!this.canAccpet(request, GameRegularEvent.GuessSize)) {
//                    response.setErrorCode(CommonCommandErrorCode.CanNotAcceptEventException);
//                    return;
//                }
//
//                //触发翻牌状态转换
//                gameController.fire(GameRegularEvent.GuessSize, gameModel);//需要下注确定翻牌
//            }
            gameModel.addGuessSizePhase(gameModel.getGameInstanceId());
            gameController.fire(GameRegularEvent.GuessSize, gameModel);//需要下注确定翻牌
            GuessSizePhaseModel phase = (GuessSizePhaseModel) gameModel.getPhase(GameRegularState.GuessSize.getValue());
            TicketResult ticketResult = phase.getPhaseData().getTicketResult();
            int prizeCash = ticketResult.getPrizeCash();//中奖金额
            int prizeLevel = ticketResult.getPrizeLevel();//奖等
            GameRegular.RegularGuessSizeResponseMsg.Builder builder = GameRegular.RegularGuessSizeResponseMsg
                    .newBuilder();
            builder.setTotalMoney(prizeCash).setFlag(prizeLevel == 1);
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

