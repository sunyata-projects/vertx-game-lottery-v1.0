package com.xt.landlords.game.eliminate;

import com.xt.landlords.GameTypes;
import com.xt.landlords.game.eliminate.phase.*;
import org.sunyata.octopus.model.GameModel;
import org.sunyata.octopus.model.GamePhaseModel;
import org.sunyata.octopus.model.PhaseState;

/**
 * Created by leo on 17/5/15.
 */
public class GameEliminateModel extends GameModel {

    public GameEliminateModel() {

    }

    public GameEliminateModel(String gameInstanceId) {
        super(GameTypes.Eliminate.getValue(), gameInstanceId);
    }

    @Override
    public Object getFirstEvent() {
        return GameEliminateEvent.Exchange;
    }

    @Override
    public Object getInitState() {
        return GameEliminateState.Init;
    }

    @Override
    public boolean needBreakPlay() {
        if (getCompleted()) {
            return false;
        }
        //游戏没有结束并且下注已经成功,此游戏需要续玩
        GamePhaseModel phase = this.getPhase(GameEliminateState.Exchange.getValue());
        boolean flag = phase != null && phase.getPhaseState() == PhaseState.InProgress;
        return flag;
    }

    @Override
    public Object getLastSuccessState() {
        return GameEliminateState.valueOf(getLastSuccessStateName());
    }

    @Override
    public boolean getCompleted() {
        return getLastSuccessStateName().equalsIgnoreCase(GameEliminateState.GameOver.getValue());
    }


    public void addOrUpdatePlayPhase(int gamePoint) {
        EliminatePlayPhaseModel phase = (EliminatePlayPhaseModel) this.getPhase(GameEliminateState.Play.getValue());
        if (phase == null) {
            EliminatePlayPhaseDataItem item = new EliminatePlayPhaseDataItem().setBetGamePoint(gamePoint);
            EliminatePlayPhaseData phaseData = new EliminatePlayPhaseData().addItem(item);
            GamePhaseModel gamePhaseModel = new EliminatePlayPhaseModel(getGameInstanceId(), GameEliminateState.Play
                    .getValue(), 2).setPhaseData(phaseData);
            addOrUpdatePhase(gamePhaseModel);
        } else {
            EliminatePlayPhaseData phaseData = phase.getPhaseData();
            EliminatePlayPhaseDataItem lastPlayPhaseDataItem = phaseData.getLastPlayPhaseDataItem();
            if (lastPlayPhaseDataItem == null) {
                EliminatePlayPhaseDataItem item = new EliminatePlayPhaseDataItem().setBetGamePoint(gamePoint);
                phaseData.addItem(item);
            } else {
                if (!lastPlayPhaseDataItem.isOver()) {
                    lastPlayPhaseDataItem.setBetGamePoint(gamePoint);
                } else {
                    EliminatePlayPhaseDataItem item = new EliminatePlayPhaseDataItem().setBetGamePoint(gamePoint);
                    phaseData.addItem(item);
                }
            }
        }
    }

    public void addClearPhase() {
        EliminateClearPhaseModel phaseModel = new EliminateClearPhaseModel(getGameInstanceId(),
                GameEliminateState.GameOver.getValue(), 3);
        EliminateClearPhaseData phaseData = new EliminateClearPhaseData();
        phaseModel.setPhaseData(phaseData);
        addOrUpdatePhase(phaseModel);
    }

    public boolean isCompleteAwardCondition() {
        EliminatePlayPhaseModel phase = (EliminatePlayPhaseModel) this.getPhase(GameEliminateState.Play.getValue());
        if (phase == null) {
            return false;
        }
        EliminatePlayPhaseData phaseData = phase.getPhaseData();
        EliminatePlayPhaseDataItem lastPlayPhaseDataItem = phaseData.getLastPlayPhaseDataItem();
        if (lastPlayPhaseDataItem == null) {
            return false;
        }
        return lastPlayPhaseDataItem.getTotalDoubleKingCount() >= 7 && lastPlayPhaseDataItem.isOver();
    }
}
