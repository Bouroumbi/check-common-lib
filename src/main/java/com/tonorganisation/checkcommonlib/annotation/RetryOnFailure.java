package com.tonorganisation.checkcommonlib.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RetryOnFailure {
    int maxAttempts() default 3;

    long delayMs() default 500;

    Class<? extends Throwable>[] include() default {};

    Class<? extends Throwable>[] exclude() default {};
}
