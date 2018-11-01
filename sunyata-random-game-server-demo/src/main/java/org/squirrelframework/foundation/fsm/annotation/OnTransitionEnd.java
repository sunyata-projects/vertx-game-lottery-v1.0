package org.squirrelframework.foundation.fsm.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Transition end listener annotation
 * @author Henry.He
 *
 */
@Target({METHOD})
@Retention(RUNTIME)
public @interface OnTransitionEnd {
    String when() default "";
}
