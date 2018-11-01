package com.xt.landlords.game.classic.phase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xt.landlords.game.classic.GameClassicState;
import org.sunyata.octopus.model.GamePhaseModel;

/**
 * Created by leo on 17/5/18.
 */
public class ClassicGuessSizePhaseModel extends GamePhaseModel<ClassicGuessSizePhaseData> {
    public ClassicGuessSizePhaseModel(String gameInstanceId, String phaseName, int orderBy) {
        super(gameInstanceId, phaseName, orderBy);
    }

    public ClassicGuessSizePhaseModel() {

    }

    public ClassicGuessSizePhaseModel(String gameInstanceId) {
        super(gameInstanceId, GameClassicState.Playing.getValue(), 5);
    }

    public ClassicGuessSizePhaseModel guess(boolean isWin) {
        ClassicGuessSizePhaseData phaseData = this.getPhaseData();
        if (phaseData == null) {
            phaseData = new ClassicGuessSizePhaseData();
        }
        phaseData.addDataItem(new ClassicGuessSizePhaseDataItem().setWin(isWin));
        return this;
    }

    public ClassicGuessSizePhaseModel enter(int type) {
        ClassicGuessSizePhaseData phaseData = this.getPhaseData();
        if (phaseData == null) {
            phaseData = new ClassicGuessSizePhaseData();
            this.setPhaseData(phaseData);
        }
        phaseData.addDataItem(new ClassicGuessSizePhaseDataItem().setType(type));

        return this;
    }

    @JsonIgnore
    public int getType() {
        ClassicGuessSizePhaseData phaseData = this.getPhaseData();
        return phaseData.getItems().get(0).getType();

    }
    @JsonIgnore
    public int getGuessCount(){
        return getPhaseData().getItems().size();
    }


    @JsonIgnore
    public boolean getLastIsWin() {
        ClassicGuessSizePhaseData phaseData = this.getPhaseData();
        ClassicGuessSizePhaseDataItem ClassicGuessSizePhaseDataItem = phaseData.getItems().get(phaseData.getItems().size
                () - 1);
        return ClassicGuessSizePhaseDataItem.isWin();
    }
}
