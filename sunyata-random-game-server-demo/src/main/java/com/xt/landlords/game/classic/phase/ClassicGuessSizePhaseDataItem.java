package com.xt.landlords.game.classic.phase;

import org.sunyata.octopus.model.PhaseData;

/**
 * Created by leo on 17/6/2.
 */
public class ClassicGuessSizePhaseDataItem extends PhaseData {

    private int type = -1;////比倍类型 0全比，1半比

    private boolean isWin;//猜大小输赢，true为赢，false为输 //第一个item不算

    public boolean isWin() {
        return isWin;
    }

    public ClassicGuessSizePhaseDataItem setWin(boolean win) {
        isWin = win;
        return this;
    }





    public int getType() {
        return type;
    }

    public ClassicGuessSizePhaseDataItem setType(int type) {
        this.type = type;
        return this;
    }
}
