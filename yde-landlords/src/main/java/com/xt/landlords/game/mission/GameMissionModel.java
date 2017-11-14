package com.xt.landlords.game.mission;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xt.landlords.game.mission.phase.MissionClearPhaseData;
import com.xt.landlords.game.mission.phase.MissionClearPhaseModel;
import com.xt.landlords.game.mission.phase.MissionPlayPhaseData;
import com.xt.landlords.game.mission.phase.MissionPlayPhaseModel;
import com.xt.landlords.game.phase.BetPhaseModel;
import com.xt.yde.GameTypes;
import org.sunyata.octopus.model.GameModel;
import org.sunyata.octopus.model.GamePhaseModel;
import org.sunyata.octopus.model.PhaseState;

import java.util.Comparator;
import java.util.Objects;

/**
 * Created by leo on 17/5/15.
 */
public class GameMissionModel extends GameModel {


    @Override
    public boolean getCompleted() {
        return getLastSuccessStateName().equalsIgnoreCase(GameMissionState.GameOver.getValue());
    }

    public GameMissionModel() {

    }

    public GameMissionModel(String gameInstanceId) {
        super(GameTypes.Mission.getValue(), gameInstanceId);
    }

//    public void addBetPhase(GameMissionPhaseName phaseName, int betAmt, String gameInstanceId) {
//        int orderBy = this.getPhaseCount() + 1;
//        GamePhaseModel gamePhaseModel = new BetPhaseModel(gameInstanceId, orderBy).setPhaseName(phaseName.Bet
//                .getValue())
//                .setPhaseData(new
//                        BetPhaseData()
//                        .setBetAmt(betAmt)).setOrderBy(orderBy).setGameInstanceId(String.valueOf(gameInstanceId));
//        this.addOrUpdatePhase(gamePhaseModel);
//    }

//    public void addDealPhase(String cardId, List<Integer> cardCenter, List<Integer> leftCenter, List<Integer>
//            rightCenter, List<Integer> underCenter) {
//        MissionPlayPhaseModel dealPhaseModel = new MissionPlayPhaseModel(getGameInstanceId(), GameMissionState.Play
// .getValue(), 2);
//        DealPhaseData dealPhaseData = new DealPhaseData();
//        dealPhaseData.setCardId(cardId);
//        dealPhaseData.setCenterCard(cardCenter);
//        dealPhaseModel.setPhaseData(dealPhaseData);
//        addOrUpdatePhase(dealPhaseModel);
//    }

    @Override
    public Object getFirstEvent() {
        return GameMissionEvent.Bet;
    }

    @Override
    public Object getInitState() {
        return GameMissionState.Init;
    }

    @Override
    public boolean needBreakPlay() {
        GamePhaseModel phase = this.getPhase(GameMissionState.Bet.getValue());
        return phase != null && phase.getPhaseState() == PhaseState.Success;
    }


    @Override
    public Object getLastSuccessState() {
        return GameMissionState.valueOf(getLastSuccessStateName());
    }

    @JsonIgnore()
    public MissionPlayPhaseModel addPlayPhase() {
        MissionPlayPhaseModel playPhaseModel = null;
        int count = (int) this.getPhases().stream().filter(p -> Objects.equals(p.getPhaseName(), GameMissionState.Play
                .getValue()))
                .count();
        playPhaseModel = new MissionPlayPhaseModel(this.getGameInstanceId(), GameMissionState.Play.getValue(),
                phases.size() + 1);
        playPhaseModel.setMissionIndex(count);
        MissionPlayPhaseData playPhaseData = new MissionPlayPhaseData();
        playPhaseModel.setPhaseData(playPhaseData);
        phases.add(playPhaseModel);
        return playPhaseModel;
    }


    public void addClearPhase() {
        int i = phases.size() + 1;
        MissionClearPhaseModel phaseModel = new MissionClearPhaseModel(getGameInstanceId(),
                GameMissionState.GameOver.getValue(), i);
        MissionClearPhaseData phaseData = new MissionClearPhaseData();
        phaseModel.setPhaseData(phaseData);
        addOrUpdatePhase(phaseModel);
    }

    @JsonIgnore()
    public float getTimes() {
        GamePhaseModel gamePhaseModel = this.getPhases().stream().filter(p -> Objects.equals(p.getPhaseName(),
                GameMissionState.Bet.getValue())).findFirst().orElse(null);
        BetPhaseModel phaseModel = (BetPhaseModel) gamePhaseModel;
        return (float) phaseModel.getPhaseData().getTicketResult().getPrizeLevel();
    }


    @JsonIgnore()
    public MissionPlayPhaseModel getLastPlayPhaseModel() {
        GamePhaseModel gamePhaseModel = this.getPhases().stream().filter(p -> Objects.equals(p.getPhaseName(),
                GameMissionState
                .Play.getValue())).max(Comparator.comparing(p -> ((MissionPlayPhaseModel) p).getMissionIndex()))
                .orElse(null);
        return (MissionPlayPhaseModel) gamePhaseModel;
    }
}
