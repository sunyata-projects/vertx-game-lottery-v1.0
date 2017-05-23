package com.xt.yde.job.message;

import org.sunyata.octopus.model.GameModel;

/**
 * Created by leo on 17/5/22.
 */
public class DefaultGameModel extends GameModel {
    @Override
    public Object getBetEvent() {
        return null;
    }

    @Override
    public Object getInitState() {
        return null;
    }

    @Override
    public boolean needBreakPlay() {
        return false;
    }

    @Override
    public boolean isGameOver() {
        return false;
    }
}
