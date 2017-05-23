package com.xt.landlords.game.regular;

import com.xt.landlords.GameTypes;
import org.sunyata.octopus.model.GameModel;
import org.sunyata.octopus.model.GamePhaseModel;
import org.sunyata.octopus.model.PhaseState;

/**
 * Created by leo on 17/5/15.
 */
public class GameRegularModel extends GameModel {
    public GameRegularModel() {

    }

    public GameRegularModel(String gameInstanceId) {
        super(GameTypes.Regular.getValue(), gameInstanceId);
    }

//    public void addBetPhase(GameRegularPhaseName phaseName, int betAmt, String gameInstanceId) {
//        int orderBy = this.getPhaseCount() + 1;
//        GamePhaseModel gamePhaseModel = new BetPhaseModel(gameInstanceId, orderBy).setPhaseName(phaseName.Bet
//                .getValue())
//                .setPhaseData(new
//                        BetPhaseData()
//                        .setBetAmt(betAmt)).setOrderBy(orderBy).setGameInstanceId(String.valueOf(gameInstanceId));
//        this.addPhase(gamePhaseModel);
//    }


    @Override
    public Object getBetEvent() {
        return GameRegularEvent.Bet;
    }

    @Override
    public Object getInitState() {
        return GameRegularState.Init;
    }

    @Override
    public boolean needBreakPlay() {
        GamePhaseModel phase = this.getPhase(GameRegularPhaseName.Bet.getValue());
        return phase != null && phase.getPhaseState() == PhaseState.Success;
    }

    @Override
    public boolean isGameOver() {
        return false;
    }
}
