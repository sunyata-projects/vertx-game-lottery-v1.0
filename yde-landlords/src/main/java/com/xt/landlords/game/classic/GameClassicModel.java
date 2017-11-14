package com.xt.landlords.game.classic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xt.landlords.game.classic.phase.*;
import com.xt.landlords.game.phase.DealPhaseData;
import com.xt.landlords.game.phase.DealPhaseModel;
import com.xt.yde.GameTypes;
import com.xt.yde.thrift.card.classic.ClassicCards;
import org.sunyata.octopus.model.GameModel;
import org.sunyata.octopus.model.GamePhaseModel;
import org.sunyata.octopus.model.PhaseState;

import java.util.Comparator;
import java.util.Objects;

/**
 * Created by leo on 17/5/15.
 */
public class GameClassicModel extends GameModel {
    @Override
    public boolean getCompleted() {
        return getLastSuccessStateName().equalsIgnoreCase(GameClassicState.GameOver.getValue());
    }

    public GameClassicModel() {

    }

    public GameClassicModel(String gameInstanceId) {
        super(GameTypes.Classic.getValue(), gameInstanceId);
    }

    public void addDealPhase(ClassicCards cards) {
       DealPhaseModel dealPhaseModel = new DealPhaseModel(getGameInstanceId(), GameClassicState.Deal
                .getValue(), 2);
        DealPhaseData dealPhaseData = new DealPhaseData();

        dealPhaseData.setCardId(cards.getCardId());

        dealPhaseData.setCenterCard(cards.getCenter());

        dealPhaseData.setRightCard(cards.getRight());


        dealPhaseData.setLeftCard(cards.getLeft());
        dealPhaseData.setDarkCard(cards.getUnder());
        //dealPhaseData.setBombNumbers(cards.getBombNums());

        dealPhaseModel.setPhaseData(dealPhaseData);
        addOrUpdatePhase(dealPhaseModel);
    }


    @Override
    public Object getFirstEvent() {
        return GameClassicEvent.Bet;
    }

    @Override
    public Object getInitState() {
        return GameClassicState.Init;
    }

    @Override
    public boolean needBreakPlay() {
        GamePhaseModel phase = this.getPhase(GameClassicState.Bet.getValue());
        return phase != null && phase.getPhaseState() == PhaseState.Success;
    }


    @Override
    public Object getLastSuccessState() {
        return GameClassicState.valueOf(getLastSuccessStateName());
    }

//    public ClassicPlayPhaseModel addOrUpdatePlayPhase() {
//        ClassicPlayPhaseModel playPhaseModel = (ClassicPlayPhaseModel) getPhase(GameClassicState.Playing
//                .getValue());
//        if (playPhaseModel == null) {
//            playPhaseModel = new ClassicPlayPhaseModel(this.getGameInstanceId());
//            ClassicPlayPhaseData playPhaseData = new ClassicPlayPhaseData();
//            playPhaseModel.setPhaseData(playPhaseData);
//            addOrUpdatePhase(playPhaseModel);
//        }
//        return playPhaseModel;
//    }

    public void addGuessSizePhase(String gameInstanceId) {
        ClassicGuessSizePhaseModel raiseBetPhaseModel = new ClassicGuessSizePhaseModel(gameInstanceId);
        raiseBetPhaseModel.setPhaseData(new ClassicGuessSizePhaseData());
        addOrUpdatePhase(raiseBetPhaseModel);
    }

//    public ClassicDragPhaseModel addDragPhaseIfNecessary() {
//        ClassicDragPhaseModel phaseModel = (ClassicDragPhaseModel) getPhase(GameClassicState.Drag.getValue());
//        if (phaseModel == null) {
//            phaseModel = new ClassicDragPhaseModel(getGameInstanceId(), GameClassicState.Drag
//                    .getValue(), 3);
//            ClassicDragPhaseData data = new ClassicDragPhaseData();
//            phaseModel.setPhaseData(data);
//            addOrUpdatePhase(phaseModel);
//        }
//        return phaseModel;
//    }

    public void addClearPhase() {
        int i = phases.size() + 1;
        ClassicClearPhaseModel phaseModel = new ClassicClearPhaseModel(getGameInstanceId(),
                GameClassicState.GameOver.getValue(), i);
        ClassicClearPhaseData phaseData = new ClassicClearPhaseData();
        phaseModel.setPhaseData(phaseData);
        addOrUpdatePhase(phaseModel);
    }

    @JsonIgnore()
    public ClassicPlayPhaseModel getLastPlayPhaseModel() {
        GamePhaseModel gamePhaseModel = this.getPhases().stream().filter(p -> Objects.equals(p.getPhaseName(),
                GameClassicState.Playing.getValue())).max(Comparator.comparing(p -> ((ClassicPlayPhaseModel) p)
                .getClassicIndex()))
                .orElse(null);
        return (ClassicPlayPhaseModel) gamePhaseModel;
    }
    public ClassicTurnPhaseModel addTurnPhase() {

        int i = phases.size() + 1;
        ClassicTurnPhaseModel phaseModel = new ClassicTurnPhaseModel(getGameInstanceId(),
                GameClassicState.Turn.getValue(), i);
        ClassicTurnPhaseData phaseData = new ClassicTurnPhaseData();
        phaseModel.setPhaseData(phaseData);
        addPhase(phaseModel);
        return phaseModel;
    }
    public ClassicPlayPhaseModel addOrUpdatePlayPhase() {
        ClassicPlayPhaseModel playPhaseModel = (ClassicPlayPhaseModel) getPhase(GameClassicState.Playing
                .getValue());
        if (playPhaseModel == null) {
            playPhaseModel = new ClassicPlayPhaseModel(this.getGameInstanceId());
            ClassicPlayPhaseData playPhaseData = new ClassicPlayPhaseData();
            playPhaseModel.setPhaseData(playPhaseData);
            addOrUpdatePhase(playPhaseModel);
        }
        return playPhaseModel;
    }
//    public void addClearPhase() {
//        ClassicClearPhaseModel phaseModel = new ClassicClearPhaseModel(getGameInstanceId(),
//                GameClassicState.GameOver.getValue(), 7);
//        ClassicClearPhaseData phaseData = new ClassicClearPhaseData();
//        phaseModel.setPhaseData(phaseData);
//        addOrUpdatePhase(phaseModel);
//    }
}
