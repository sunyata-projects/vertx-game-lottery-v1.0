package com.xt.landlords.game.puzzle.command;

import com.xt.landlords.*;
import com.xt.landlords.game.puzzle.GamePuzzleEvent;
import com.xt.landlords.game.puzzle.GamePuzzleModel;
import com.xt.landlords.game.puzzle.GamePuzzleState;
import com.xt.landlords.game.puzzle.phase.PuzzleDealPhaseData;
import com.xt.landlords.statemachine.GameController;
import com.xt.yde.protobuf.puzzle.GamePuzzle;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.ImmutableState;
import org.sunyata.octopus.OctopusRequest;
import org.sunyata.octopus.OctopusResponse;

/**
 * Created by leo on 17/5/15.
 */
@Component(Commands.PuzzleClear)
public class PuzzleClearCommandHandler extends AbstractGameControllerCommandHandler {
    org.slf4j.Logger logger = LoggerFactory.getLogger(PuzzleClearCommandHandler.class);

    class CommandErrorCode {

    }

    @Autowired
    StoreManager storeManager;

    @Override
    public void execute(OctopusRequest request, OctopusResponse response) throws Exception {
        try {
            if (!this.canAccpet(request, GamePuzzleEvent.GameOver)) {
                response.setErrorCode(CommonCommandErrorCode.CanNotAcceptEventException);
                return;
            }
            GameController gameController = GameManager.getGameController(request.getSession().getCurrentUser()
                    .getName());
            if (gameController == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }
            GamePuzzleModel gameModel = (GamePuzzleModel) gameController.getGameModel();
            if (gameModel == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }
            gameModel.addClearPhase();
            GamePuzzle.ClearGameResponseMsg.Builder builder = GamePuzzle.ClearGameResponseMsg.newBuilder();
            PuzzleDealPhaseData phaseData = (PuzzleDealPhaseData) gameController.getPhaseData(GamePuzzleState.Deal.getValue());
            builder.setTotalMoney(phaseData.getTotalMoney());
            gameController.fire(GamePuzzleEvent.GameOver, gameModel);
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

