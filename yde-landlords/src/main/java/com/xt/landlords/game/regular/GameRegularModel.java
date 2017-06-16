package com.xt.landlords.game.regular;

import com.xt.landlords.GameTypes;
import com.xt.landlords.game.phase.DealPhaseData;
import com.xt.landlords.game.phase.DealPhaseModel;
import com.xt.landlords.game.regular.phase.*;
import org.sunyata.octopus.model.GameModel;
import org.sunyata.octopus.model.GamePhaseModel;
import org.sunyata.octopus.model.PhaseState;

import java.util.List;

/**
 * Created by leo on 17/5/15.
 */
public class GameRegularModel extends GameModel {
    @Override
    public boolean getCompleted() {
        return getLastSuccessStateName().equalsIgnoreCase(GameRegularState.GameOver.getValue());
    }

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
//        this.addOrUpdatePhase(gamePhaseModel);
//    }

    public void addRaisePhase(String gameInstanceId, int times, int betAmt) {
        RaisePhaseModel raiseBetPhaseModel = new RaisePhaseModel(gameInstanceId, GameRegularState.Raise.getValue(), 3);
        raiseBetPhaseModel.setPhaseData(new RaisePhaseData().setTimes(times).setBetAmt(betAmt));
        addOrUpdatePhase(raiseBetPhaseModel);
    }

    public void addDealPhase(String cardId, List<Integer> cardCenter) {
        DealPhaseModel dealPhaseModel = new DealPhaseModel(getGameInstanceId(), GameRegularState.Deal.getValue(), 2);
        DealPhaseData dealPhaseData = new DealPhaseData();
        dealPhaseData.setCardId(cardId);
        dealPhaseData.setCenterCard(cardCenter);
        dealPhaseModel.setPhaseData(dealPhaseData);
        addOrUpdatePhase(dealPhaseModel);
    }

    public void addDarkPhase(String cardId, List<Integer> cardCenter, List<Integer> cardRight,
                             List<Integer>
                                     cardLeft,
                             List<Integer> cardUnder) {
        DealPhaseModel darkPhaseModel = new DealPhaseModel(this.getGameInstanceId(), GameRegularState.Dark.getValue(),
                4);
        DealPhaseData darkPhaseData = new DealPhaseData();
        darkPhaseData
                .setCardId(cardId)
                .setCenterCard(cardCenter)
                .setLeftCard(cardLeft)
                .setRightCard(cardRight)
                .setDarkCard(cardUnder);
        darkPhaseModel.setPhaseData(darkPhaseData);
        addOrUpdatePhase(darkPhaseModel);
    }

    @Override
    public Object getFirstEvent() {
        return GameRegularEvent.Bet;
    }

    @Override
    public Object getInitState() {
        return GameRegularState.Init;
    }

    @Override
    public boolean needBreakPlay() {
        GamePhaseModel phase = this.getPhase(GameRegularState.Bet.getValue());
        return phase != null && phase.getPhaseState() == PhaseState.Success;
    }


    @Override
    public Object getLastSuccessState() {
        return GameRegularState.valueOf(getLastSuccessStateName());
    }

    public RegularPlayPhaseModel addOrUpdatePlayPhase() {
        RegularPlayPhaseModel playPhaseModel = (RegularPlayPhaseModel) getPhase(GameRegularState.Playing
                .getValue());
        if (playPhaseModel == null) {
            playPhaseModel = new RegularPlayPhaseModel(this.getGameInstanceId());
            RegularPlayPhaseData playPhaseData = new RegularPlayPhaseData();
            playPhaseModel.setPhaseData(playPhaseData);
            addOrUpdatePhase(playPhaseModel);
        }
        return playPhaseModel;
    }

    public void addGuessSizePhase(String gameInstanceId) {
        GuessSizePhaseModel raiseBetPhaseModel = new GuessSizePhaseModel(gameInstanceId);
        raiseBetPhaseModel.setPhaseData(new GuessSizePhaseData());
        addOrUpdatePhase(raiseBetPhaseModel);
    }


    public void addClearPhase() {
        RegularClearPhaseModel phaseModel = new RegularClearPhaseModel(getGameInstanceId(),
                GameRegularState.GameOver.getValue(), 7);
        RegularClearPhaseData phaseData = new RegularClearPhaseData();
        phaseModel.setPhaseData(phaseData);
        addOrUpdatePhase(phaseModel);
    }
}
