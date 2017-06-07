package com.xt.landlords.game.puzzle;

import com.xt.landlords.GameTypes;
import com.xt.landlords.game.puzzle.phase.PuzzleClearPhaseModel;
import com.xt.landlords.game.puzzle.phase.PuzzleDealPhaseData;
import com.xt.landlords.game.puzzle.phase.PuzzleDealPhaseModel;
import org.sunyata.octopus.model.GameModel;
import org.sunyata.octopus.model.GamePhaseModel;
import org.sunyata.octopus.model.PhaseState;

/**
 * Created by leo on 17/5/15.
 */
public class GamePuzzleModel extends GameModel {

    public GamePuzzleModel() {

    }

    public GamePuzzleModel(String gameInstanceId) {
        super(GameTypes.Puzzle.getValue(), gameInstanceId);
    }

    @Override
    public Object getFirstEvent() {
        return GamePuzzleEvent.Bet;
    }

    @Override
    public Object getInitState() {
        return GamePuzzleState.Init;
    }

    @Override
    public boolean needBreakPlay() {
        if (getCompleted()) {
            return false;
        }
        //游戏没有结束并且下注已经成功,此游戏需要续玩
        GamePhaseModel phase = this.getPhase(GamePuzzleState.Bet.getValue());
        boolean flag = phase != null && phase.getPhaseState() == PhaseState.Success;
        return flag;
    }

    @Override
    public Object getLastSuccessState() {
        return GamePuzzleState.valueOf(getLastSuccessStateName());
    }

    @Override
    public boolean getCompleted() {
        return getLastSuccessStateName().equalsIgnoreCase(GamePuzzleState.GameOver.getValue());
    }


    public void addDealPhase(String cardId, int money) {
        PuzzleDealPhaseData phaseData = new PuzzleDealPhaseData().setTotalMoney(money).setCardId("cardId");
        GamePhaseModel gamePhaseModel = new PuzzleDealPhaseModel(getGameInstanceId(), GamePuzzleState.Deal.getValue
                (), 2).setPhaseData(phaseData);
        addOrUpdatePhase(gamePhaseModel);
    }

    public void addClearPhase() {
        PuzzleClearPhaseModel phaseModel = new PuzzleClearPhaseModel(getGameInstanceId(),
                GamePuzzleState.GameOver.getValue(), 3);
        addOrUpdatePhase(phaseModel);
    }
}
