package com.xt.landlords.game.command;

import com.xt.landlords.*;
import com.xt.landlords.game.phase.ExchangePhaseData;
import com.xt.landlords.game.phase.ExchangePhaseModel;
import com.xt.landlords.service.MoneyBetService;
import com.xt.landlords.statemachine.GameController;
import com.xt.yde.protobuf.common.Common;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.ImmutableState;
import org.sunyata.octopus.OctopusRequest;
import org.sunyata.octopus.OctopusResponse;
import org.sunyata.octopus.model.GameModel;
import org.sunyata.octopus.model.GamePhaseModel;
import org.sunyata.octopus.model.PhaseState;

/**
 * Created by leo on 17/4/18.
 */
@Component(Commands.Exchange)
public class ExchangeCommandHandler extends AbstractAuthCommandHandler {
    org.slf4j.Logger logger = LoggerFactory.getLogger(ExchangeCommandHandler.class);

    class CommandErrorCode {

    }

    @Autowired
    MoneyBetService moneyBetService;

    @Autowired
    GameManager gameManager;

    @Autowired
    StoreManager storeManager;

    @Override
    public void execute(OctopusRequest request, OctopusResponse response) throws Exception {
        try {
            String userName = request.getSession().getCurrentUser().getName();
            Common.ExchangeGamePointRequestMsg exchangeGamePointRequestMsg = Common.ExchangeGamePointRequestMsg
                    .parseFrom(request.getMessage()
                            .getBody());
            GameModel gameModel = null;

            GameController gameController = GameManager.getGameController(userName);
            if (gameController != null) {
                Integer gameType = gameController.getGameType();
                //游戏类型是否匹配
                if (!gameType.equals(exchangeGamePointRequestMsg.getGameType())) {
                    response.setErrorCode(CommonCommandErrorCode.GameTypeError);
                    return;
                }
                //是否可接受exchange Event
                boolean canAccept = gameController.canAccept(GameCommonState.Exchange.getValue());
                if (!canAccept) {
                    response.setErrorCode(CommonCommandErrorCode.CanNotAcceptEventException);
                    return;
                }
                gameModel = gameController.getGameModel();
                if (gameModel == null) {
                    response.setErrorCode(CommonCommandErrorCode.InternalError);
                    return;
                }
                GamePhaseModel phase = gameController.getPhase(GameCommonState.Exchange.getValue());//.Bet.getValue());
                if (phase.getPhaseState() == PhaseState.Success) {//兑换成功
                    response.setErrorCode(CommonCommandErrorCode.CanNotRepeatExchange);
                    return;
                }
            } else {
                gameModel = gameManager.createGameModelAndBetPhase(exchangeGamePointRequestMsg.getGameType(),
                        userName,
                        exchangeGamePointRequestMsg.getAmt());

                gameController = GameManager.createGameController(exchangeGamePointRequestMsg.getGameType(),
                        (GameControllerState)
                                gameModel.getLastSuccessState());
                gameController.setGameModel(gameModel);
                GameManager.put(userName, gameController);
                gameController.start();
            }

            gameController.fire(gameController.getFirstEvent(), gameModel);
            ImmutableState currentRawState = gameController.getCurrentRawState();
            logger.info("{}:currentState:{}", this.getClass().getName(), currentRawState);
            //获取兑换的游戏点数,返回给前端
            ExchangePhaseModel exchangePhase = (ExchangePhaseModel) gameModel.getPhase(GameCommonState.Exchange
                    .getValue());
            ExchangePhaseData exchangePhaseData = exchangePhase.getPhaseData();
            Common.ExchangeGamePointResponseMsg build = Common.ExchangeGamePointResponseMsg.newBuilder().setGamePoint
                    (exchangePhaseData.getGamePoint()).build();
            response.setBody(build.toByteArray());
        } catch (Exception ex) {
            ExceptionProcessor.process(response, ex);
        } finally {
            response.writeAndFlush();
        }
    }

    @Override
    public boolean isAsync() {
        return true;
    }
}
