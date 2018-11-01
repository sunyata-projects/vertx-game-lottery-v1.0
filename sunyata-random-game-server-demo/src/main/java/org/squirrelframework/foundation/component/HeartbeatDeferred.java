package org.squirrelframework.foundation.component;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(METHOD)
@Retention(RUNTIME)
public @interface HeartbeatDeferred {
    // TODO-hhe: declarative way to add deferred command
}
