package com.xt.landlords.game.regular.condition;

import com.xt.landlords.statemachine.MyContext;
import org.squirrelframework.foundation.fsm.AnonymousCondition;

/**
 * Created by leo on 17/4/26.
 */
public class WinCondition extends AnonymousCondition<MyContext> {

    @Override
    public boolean isSatisfied(MyContext context) {
        return true;
    }
}

