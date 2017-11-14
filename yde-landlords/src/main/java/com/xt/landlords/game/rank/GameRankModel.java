package com.xt.landlords.game.rank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xt.landlords.game.phase.BetPhaseModel;
import com.xt.landlords.game.phase.DealPhaseData;
import com.xt.landlords.game.phase.DealPhaseModel;
import com.xt.landlords.game.phase.TicketResult;
import com.xt.landlords.game.rank.phase.*;
import com.xt.yde.GameTypes;
import org.sunyata.octopus.model.GameModel;
import org.sunyata.octopus.model.GamePhaseModel;
import org.sunyata.octopus.model.PhaseState;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class GameRankModel extends GameModel {

    @Override
    public boolean getCompleted() {
        return getLastSuccessStateName().equalsIgnoreCase(GameRankState.GameOver.getValue());
    }

    public GameRankModel() {

    }

    public GameRankModel(String gameInstanceId) {
        super(GameTypes.Rank.getValue(), gameInstanceId);
    }

//    public void addBetPhase(GameRankPhaseName phaseName, int betAmt, String gameInstanceId) {
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
//        RankPlayPhaseModel dealPhaseModel = new RankPlayPhaseModel(getGameInstanceId(), GameRankState.Play
// .getValue(), 2);
//        DealPhaseData dealPhaseData = new DealPhaseData();
//        dealPhaseData.setCardId(cardId);
//        dealPhaseData.setCenterCard(cardCenter);
//        dealPhaseModel.setPhaseData(dealPhaseData);
//        addOrUpdatePhase(dealPhaseModel);
//    }

    @Override
    public Object getFirstEvent() {
        return GameRankEvent.Bet;
    }

    @Override
    public Object getInitState() {
        return GameRankState.Init;
    }

    @Override
    public boolean needBreakPlay() {
        GamePhaseModel phase = this.getPhase(GameRankState.Bet.getValue());
        return phase != null && phase.getPhaseState() == PhaseState.Success;
    }


    @Override
    public Object getLastSuccessState() {
        return GameRankState.valueOf(getLastSuccessStateName());
    }

    @JsonIgnore()
    public RankPlayPhaseModel addPlayPhase() {
        RankPlayPhaseModel playPhaseModel = null;
        int count = (int) this.getPhases().stream().filter(p -> Objects.equals(p.getPhaseName(), GameRankState.Playing
                .getValue()))
                .count();
        playPhaseModel = new RankPlayPhaseModel(this.getGameInstanceId(), GameRankState.Playing.getValue(),
                phases.size() + 1);
        playPhaseModel.setRankIndex(count+1);
        RankPlayPhaseData playPhaseData = new RankPlayPhaseData();
        playPhaseModel.setPhaseData(playPhaseData);
        phases.add(playPhaseModel);
        return playPhaseModel;
    }


    public void addClearPhase() {
        int i = phases.size() + 1;
        RankClearPhaseModel phaseModel = new RankClearPhaseModel(getGameInstanceId(),
                GameRankState.GameOver.getValue(), i);
        RankClearPhaseData phaseData = new RankClearPhaseData();
        phaseModel.setPhaseData(phaseData);
        addOrUpdatePhase(phaseModel);
    }

    @JsonIgnore()
    public TicketResult getPrizeLevel(int rankIndex) {
        GamePhaseModel gamePhaseModel = this.getPhases().stream().filter(p -> Objects.equals(p.getPhaseName(),
                GameRankState.Bet.getValue())).findFirst().orElse(null);
        BetPhaseModel phaseModel = (BetPhaseModel) gamePhaseModel;
        List<TicketResult> ticketResults = phaseModel.getPhaseData().getTicketResults();
        if (rankIndex < ticketResults.size()) {
            return ticketResults.get(rankIndex);
        }
        return null;
    }


    @JsonIgnore()
    public RankPlayPhaseModel getLastPlayPhaseModel() {
        GamePhaseModel gamePhaseModel = this.getPhases().stream().filter(p -> Objects.equals(p.getPhaseName(),
                GameRankState.Playing.getValue())).max(Comparator.comparing(p -> ((RankPlayPhaseModel) p)
                .getRankIndex()))
                .orElse(null);
        return (RankPlayPhaseModel) gamePhaseModel;
    }

    @JsonIgnore()
    public DealPhaseModel getLastDealPhaseModel() {
        GamePhaseModel gamePhaseModel = this.getPhases().stream().filter(p -> Objects.equals(p.getPhaseName(),
                GameRankState.Deal.getValue())).max(Comparator.comparing(p -> ((DealPhaseModel) p)
                .getOrderBy()))
                .orElse(null);
        return (DealPhaseModel) gamePhaseModel;
    }

    @JsonIgnore()
    public boolean getIsOver() {
        long count = this.getPhases().stream().filter(p -> Objects.equals(p.getPhaseName(),
                GameRankState.Playing.getValue())).count();

        return count == 3;
    }

    public RankTurnPhaseModel addTurnPhase() {

        int i = phases.size() + 1;
        RankTurnPhaseModel phaseModel = new RankTurnPhaseModel(getGameInstanceId(),
                GameRankState.Turn.getValue(), i);
        RankTurnPhaseData phaseData = new RankTurnPhaseData();
        phaseModel.setPhaseData(phaseData);
        addPhase(phaseModel);
        return phaseModel;
    }

    public DealPhaseModel addDealPhase() {
        int i = phases.size() + 1;
        DealPhaseModel phaseModel = new DealPhaseModel(getGameInstanceId(),
                GameRankState.Deal.getValue(), i);
        DealPhaseData phaseData = new DealPhaseData();
        phaseModel.setPhaseData(phaseData);
        addPhase(phaseModel);
        return phaseModel;
    }

//    public RankPlayPhaseModel addPlayPhase() {
//        RankPlayPhaseModel playPhaseModel = null;
////        int count = (int) this.getPhases().stream().filter(p -> Objects.equals(p.getPhaseName(), GameMissionState
// .Play
////                .getValue()))
////                .count();
//        playPhaseModel = new RankPlayPhaseModel(this.getGameInstanceId(), GameMissionState.Play.getValue(),
//                phases.size() + 1);
//        //playPhaseModel.setMissionIndex(count);
//        RankPlayPhaseData playPhaseData = new RankPlayPhaseData();
//        playPhaseModel.setPhaseData(playPhaseData);
//        phases.add(playPhaseModel);
//        return playPhaseModel;
//    }
}

