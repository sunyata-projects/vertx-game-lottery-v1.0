package com.xt.landlords.game.crazy;

import com.xt.landlords.game.crazy.phase.*;
import com.xt.landlords.game.regular.phase.GuessSizePhaseData;
import com.xt.landlords.game.regular.phase.GuessSizePhaseModel;
import com.xt.yde.GameTypes;
import com.xt.yde.thrift.card.crazy.CrazyCards;
import org.sunyata.octopus.model.GameModel;
import org.sunyata.octopus.model.GamePhaseModel;
import org.sunyata.octopus.model.PhaseState;

/**
 * Created by leo on 17/5/15.
 */
public class GameCrazyModel extends GameModel {
    @Override
    public boolean getCompleted() {
        return getLastSuccessStateName().equalsIgnoreCase(GameCrazyState.GameOver.getValue());
    }

    public GameCrazyModel() {

    }

    public GameCrazyModel(String gameInstanceId) {
        super(GameTypes.Crazy.getValue(), gameInstanceId);
    }

    public void addDealPhase(CrazyCards cards) {
        CrazyDealPhaseModel dealPhaseModel = new CrazyDealPhaseModel(getGameInstanceId(), GameCrazyState.Deal
                .getValue(), 2);
        CrazyDealPhaseData dealPhaseData = new CrazyDealPhaseData();

        dealPhaseData.setCardId(cards.getCardId());

        dealPhaseData.setCenterCard(cards.getCenter());
        dealPhaseData.setCenterThreeCard(cards.getCenterThree());

        dealPhaseData.setRightCard(cards.getRight());
        dealPhaseData.setRightOneCard(cards.getRightOne());

        dealPhaseData.setLeftCard(cards.getLeft());
        dealPhaseData.setLeftOneCard(cards.getLeftOne());

        dealPhaseData.setBombNumbers(cards.getBombNums());

        dealPhaseModel.setPhaseData(dealPhaseData);
        addOrUpdatePhase(dealPhaseModel);
    }


    @Override
    public Object getFirstEvent() {
        return GameCrazyEvent.Bet;
    }

    @Override
    public Object getInitState() {
        return GameCrazyState.Init;
    }

    @Override
    public boolean needBreakPlay() {
        GamePhaseModel phase = this.getPhase(GameCrazyState.Bet.getValue());
        return phase != null && phase.getPhaseState() == PhaseState.Success;
    }


    @Override
    public Object getLastSuccessState() {
        return GameCrazyState.valueOf(getLastSuccessStateName());
    }

//    public CrazyPlayPhaseModel addOrUpdatePlayPhase() {
//        CrazyPlayPhaseModel playPhaseModel = (CrazyPlayPhaseModel) getPhase(GameCrazyState.Playing
//                .getValue());
//        if (playPhaseModel == null) {
//            playPhaseModel = new CrazyPlayPhaseModel(this.getGameInstanceId());
//            CrazyPlayPhaseData playPhaseData = new CrazyPlayPhaseData();
//            playPhaseModel.setPhaseData(playPhaseData);
//            addOrUpdatePhase(playPhaseModel);
//        }
//        return playPhaseModel;
//    }

    public void addGuessSizePhase(String gameInstanceId) {
        GuessSizePhaseModel raiseBetPhaseModel = new GuessSizePhaseModel(gameInstanceId);
        raiseBetPhaseModel.setPhaseData(new GuessSizePhaseData());
        addOrUpdatePhase(raiseBetPhaseModel);
    }

    public CrazyDragPhaseModel addDragPhaseIfNecessary() {
        CrazyDragPhaseModel phaseModel = (CrazyDragPhaseModel) getPhase(GameCrazyState.Drag.getValue());
        if (phaseModel == null) {
            phaseModel = new CrazyDragPhaseModel(getGameInstanceId(), GameCrazyState.Drag
                    .getValue(), 3);
            CrazyDragPhaseData data = new CrazyDragPhaseData();
            phaseModel.setPhaseData(data);
            addOrUpdatePhase(phaseModel);
        }
        return phaseModel;
    }

    public void addClearPhase() {
        int i = phases.size() + 1;
        CrazyClearPhaseModel phaseModel = new CrazyClearPhaseModel(getGameInstanceId(),
                GameCrazyState.GameOver.getValue(), i);
        CrazyClearPhaseData phaseData = new CrazyClearPhaseData();
        phaseModel.setPhaseData(phaseData);
        addOrUpdatePhase(phaseModel);
    }


//    public void addClearPhase() {
//        CrazyClearPhaseModel phaseModel = new CrazyClearPhaseModel(getGameInstanceId(),
//                GameCrazyState.GameOver.getValue(), 7);
//        CrazyClearPhaseData phaseData = new CrazyClearPhaseData();
//        phaseModel.setPhaseData(phaseData);
//        addOrUpdatePhase(phaseModel);
//    }
}
