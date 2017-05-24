package com.xt.landlords.game.puzzle.command;

import com.xt.landlords.*;
import com.xt.landlords.game.puzzle.GamePuzzleEvent;
import com.xt.landlords.game.puzzle.GamePuzzleState;
import com.xt.landlords.game.puzzle.phase.PuzzleClearPhaseModel;
import com.xt.landlords.game.puzzle.phase.PuzzleDealPhaseData;
import com.xt.landlords.statemachine.GameController;
import com.xt.yde.protobuf.puzzle.GamePuzzle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.octopus.OctopusRequest;
import org.sunyata.octopus.OctopusResponse;
import org.sunyata.octopus.model.GameModel;
import org.sunyata.octopus.model.GamePhaseModel;

/**
 * Created by leo on 17/5/15.
 */
@Component(Commands.PuzzleClear)
public class PuzzleClearCommandHandler extends AbstractGameControllerCommandHandler {
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
            GameModel gameModel = storeManager.getGameModelFromCache(request.getSession().getCurrentUser()
                    .getName());
            if (gameController == null || gameModel == null) {
                response.setErrorCode(CommonCommandErrorCode.InternalError);
                return;
            }

            GamePuzzle.ClearGameResponseMsg.Builder builder = GamePuzzle.ClearGameResponseMsg.newBuilder();

            GamePhaseModel<PuzzleDealPhaseData> deaLPhase = gameModel.getPhase(GamePuzzleState.Deal.getValue());
            PuzzleDealPhaseData phaseData = deaLPhase.getPhaseData();
            PuzzleClearPhaseModel phaseModel = new PuzzleClearPhaseModel(gameModel.getGameInstanceId(),
                    GamePuzzleState.GameOver.getValue(), 3);
            gameModel.addOrUpdatePhase(phaseModel);
            builder.setTotalMoney(phaseData.getTotalMoney());
            gameController.fire(GamePuzzleEvent.GameOver, gameModel);
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

