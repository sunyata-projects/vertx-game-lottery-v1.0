package org.squirrelframework.foundation.fsm;

import java.util.List;
/**
 * Constants conditions
 * 
 * @author Henry.He
 *
 */
public class Conditions {
    
    public static <C> boolean isSatified(org.squirrelframework.foundation.fsm.Condition<C> condition, C context) {
        return condition!=null && context!=null && condition.isSatisfied(context);
    }
    
    public static <C> boolean isNotSatified(org.squirrelframework.foundation.fsm.Condition<C> condition, C context) {
        return condition==null || context==null || !condition.isSatisfied(context);
    }
    
    public static class Always<C> extends AnonymousCondition<C> {
        @Override
        public boolean isSatisfied(C context) {
            return true;
        }
    }
    
    public static <C> org.squirrelframework.foundation.fsm.Condition<C> always()  {
        return new Always<C>();
    }
    
    public static class Never<C> extends AnonymousCondition<C> {
        @Override
        public boolean isSatisfied(C context) {
            return false;
        }
    }
    
    public static <C> org.squirrelframework.foundation.fsm.Condition<C> never() {
        return new Never<C>();
    }

    public static <C> org.squirrelframework.foundation.fsm.Condition<C> and(final org.squirrelframework.foundation.fsm.Condition<C> first, final org.squirrelframework.foundation.fsm.Condition<C> second) {
        return new org.squirrelframework.foundation.fsm.Condition<C>() {
            @Override
            public boolean isSatisfied(C context) {
                return first.isSatisfied(context) && second.isSatisfied(context);
            }
            
            @Override
            public String name() {
                return first.name()+"And"+second.name();
            }
        };
    }

    public static <C> org.squirrelframework.foundation.fsm.Condition<C> and(final List<org.squirrelframework.foundation.fsm.Condition<C>> conditions) {
        return new org.squirrelframework.foundation.fsm.Condition<C>() {
            @Override
            public boolean isSatisfied(C context) {
                for (org.squirrelframework.foundation.fsm.Condition<C> condition : conditions) {
                    if (!condition.isSatisfied(context)) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public String name() {
                String name = null;
                for(org.squirrelframework.foundation.fsm.Condition<C> c : conditions) {
                    if(name==null) 
                        name=c.name();
                    else 
                        name = name+"And"+c.name();
                }
                return name;
            }
        };
    }

    public static <C> org.squirrelframework.foundation.fsm.Condition<C> or(final org.squirrelframework.foundation.fsm.Condition<C> first, final org.squirrelframework.foundation.fsm.Condition<C> second) {
        return new org.squirrelframework.foundation.fsm.Condition<C>() {
            @Override
            public boolean isSatisfied(C context) {
                return first.isSatisfied(context) || second.isSatisfied(context);
            }

            @Override
            public String name() {
                return first.name()+"Or"+second.name();
            }
        };
    }

    public static <C> org.squirrelframework.foundation.fsm.Condition<C> or(final List<org.squirrelframework.foundation.fsm.Condition<C>> conditions) {
        return new org.squirrelframework.foundation.fsm.Condition<C>() {
            @Override
            public boolean isSatisfied(C context) {
                for (org.squirrelframework.foundation.fsm.Condition<C> condition : conditions) {
                    if (condition.isSatisfied(context)) {
                        return true;
                    }
                }
                return false;
            }
            
            @Override
            public String name() {
                String name = null;
                for(org.squirrelframework.foundation.fsm.Condition<C> c : conditions) {
                    if(name==null) 
                        name=c.name();
                    else 
                        name = name+"Or"+c.name();
                }
                return name;
            }
        };
    }

    public static <C> org.squirrelframework.foundation.fsm.Condition<C> not(final org.squirrelframework.foundation.fsm.Condition<C> condition) {
        return new org.squirrelframework.foundation.fsm.Condition<C>() {
            @Override
            public boolean isSatisfied(C context) {
                return !condition.isSatisfied(context);
            }

            @Override
            public String name() {
                return "Not"+condition.name();
            }
        };
    }

    public static <C> org.squirrelframework.foundation.fsm.Condition<C> xor(final org.squirrelframework.foundation.fsm.Condition<C> first, final org.squirrelframework.foundation.fsm.Condition<C> second) {
        return new org.squirrelframework.foundation.fsm.Condition<C>() {
            @Override
            public boolean isSatisfied(C context) {
                return first.isSatisfied(context) ^ second.isSatisfied(context);
            }

            @Override
            public String name() {
                return first.name()+"Xor"+second.name();
            }
        };
    }
}
