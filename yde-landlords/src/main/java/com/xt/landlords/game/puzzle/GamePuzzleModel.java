package com.xt.landlords.game.puzzle;

import com.xt.landlords.GameTypes;
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
    public Object getBetEvent() {
        return GamePuzzleEvent.Bet;
    }

    @Override
    public Object getInitState() {
        return GamePuzzleState.Init;
    }

    @Override
    public boolean needBreakPlay() {
        if (isGameOver()) {
            return false;
        }
        //游戏没有结束并且下注已经成功,此游戏需要续玩
        GamePhaseModel phase = this.getPhase(GamePuzzleState.Bet.getValue());
        boolean flag = phase != null && phase.getPhaseState() == PhaseState.Success;
        return flag;
    }

    @Override
    public boolean isGameOver() {
        GamePhaseModel phase = this.getPhase(GamePuzzleState.GameOver.getValue());
        return phase != null && phase.getPhaseState() == PhaseState.Success;
    }

}
