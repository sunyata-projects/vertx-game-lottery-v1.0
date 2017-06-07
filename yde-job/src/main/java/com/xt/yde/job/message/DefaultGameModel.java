package com.xt.yde.job.message;

import org.sunyata.octopus.model.GameModel;

/**
 * Created by leo on 17/5/22.
 */
public class DefaultGameModel extends GameModel {

    @Override
    public boolean getCompleted() {
        return getLastSuccessStateName().equalsIgnoreCase("GameOver");
    }


    @Override
    public Object getFirstEvent() {
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
    public Object getLastSuccessState() {
        return null;
    }
}
