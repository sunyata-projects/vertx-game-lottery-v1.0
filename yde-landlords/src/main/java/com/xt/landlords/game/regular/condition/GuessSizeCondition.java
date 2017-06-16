package com.xt.landlords.game.regular.condition;

import com.xt.landlords.game.regular.GameRegularModel;
import org.squirrelframework.foundation.fsm.AnonymousCondition;

/**
 * Created by leo on 17/4/26.
 */
public class GuessSizeCondition extends AnonymousCondition<GameRegularModel> {

    @Override
    public boolean isSatisfied(GameRegularModel context) {
        return true;
    }
}

