package com.xt.landlords.game.classic.phase;

import com.xt.landlords.game.classic.GameClassicState;
import org.sunyata.octopus.model.GamePhaseModel;

/**
 * Created by leo on 17/5/16.
 */
public class ClassicPlayPhaseModel extends GamePhaseModel<ClassicPlayPhaseData> {

    private int ClassicIndex;

    public ClassicPlayPhaseModel() {

    }

    public ClassicPlayPhaseModel(String gameInstanceId) {
        super(gameInstanceId, GameClassicState.Playing.getValue(), 5);
    }
    public ClassicPlayPhaseModel(String gameInstanceId, String phaseName, int orderBy) {
        super(gameInstanceId, phaseName, orderBy);
    }

    public void setClassicIndex(int ClassicIndex) {
        this.ClassicIndex = ClassicIndex;
    }

    public int getClassicIndex() {
        return ClassicIndex;
    }


}
