package com.xt.landlords.game.crazy.phase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.sunyata.octopus.model.GamePhaseModel;

/**
 * Created by leo on 17/5/18.
 */
public class CrazyGuessSizePhaseModel extends GamePhaseModel<CrazyGuessSizePhaseData> {
    public CrazyGuessSizePhaseModel(String gameInstanceId, String phaseName, int orderBy) {
        super(gameInstanceId, phaseName, orderBy);
    }

    public CrazyGuessSizePhaseModel() {

    }

    public CrazyGuessSizePhaseModel guess(boolean isWin) {
        CrazyGuessSizePhaseData phaseData = this.getPhaseData();
        if (phaseData == null) {
            phaseData = new CrazyGuessSizePhaseData();
        }
        phaseData.addDataItem(new CrazyGuessSizePhaseDataItem().setWin(isWin));
        return this;
    }

    public CrazyGuessSizePhaseModel enter(int type) {
        CrazyGuessSizePhaseData phaseData = this.getPhaseData();
        if (phaseData == null) {
            phaseData = new CrazyGuessSizePhaseData();
            this.setPhaseData(phaseData);
        }
        phaseData.addDataItem(new CrazyGuessSizePhaseDataItem().setType(type));

        return this;
    }

    @JsonIgnore
    public int getType() {
        CrazyGuessSizePhaseData phaseData = this.getPhaseData();
        return phaseData.getItems().get(0).getType();

    }
    @JsonIgnore
    public int getGuessCount(){
        return getPhaseData().getItems().size();
    }


    @JsonIgnore
    public boolean getLastIsWin() {
        CrazyGuessSizePhaseData phaseData = this.getPhaseData();
        CrazyGuessSizePhaseDataItem crazyGuessSizePhaseDataItem = phaseData.getItems().get(phaseData.getItems().size
                () - 1);
        return crazyGuessSizePhaseDataItem.isWin();
    }
}
