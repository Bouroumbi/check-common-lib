package com.tonorganisation.checkcommonlib.aspect;

import com.tonorganisation.checkcommonlib.annotation.RetryOnFailure;
import com.tonorganisation.checkcommonlib.properties.RetryProperties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Slf4j
public class RetryAspect {

    private final RetryProperties retryProperties;

    public RetryAspect(RetryProperties retryProperties) {
        this.retryProperties = retryProperties;
    }

    @Around("@annotation(retryAnnotation)")
    public Object retry(ProceedingJoinPoint joinPoint, RetryOnFailure retryAnnotation) throws Throwable {
        int maxAttempts = retryAnnotation.maxAttempts() > 0
                ? retryAnnotation.maxAttempts()
                : retryProperties.getDefaultMaxAttempts();

        long delayMs = retryAnnotation.delayMs() > 0
                ? retryAnnotation.delayMs()
                : retryProperties.getDefaultDelayMs();

        Class<? extends Throwable>[] includes = retryAnnotation.include();
        Class<? extends Throwable>[] excludes = retryAnnotation.exclude();

        int attempt = 0;
        Throwable lastException = null;

        while (attempt < maxAttempts) {
            try {
                return joinPoint.proceed();
            } catch (Throwable ex) {
                attempt++;
                lastException = ex;

                boolean shouldRetry = shouldRetryForException(ex, includes, excludes);

                if (!shouldRetry)
                    throw ex;

                log.warn("[RETRY] Tentative {} échouée pour {} : {}", attempt, joinPoint.getSignature(),
                        ex.getMessage());

                if (attempt < maxAttempts) {
                    Thread.sleep(delayMs);
                }
            }
        }

        throw lastException;
    }

    private boolean shouldRetryForException(Throwable ex, Class<? extends Throwable>[] include,
            Class<? extends Throwable>[] exclude) {
        for (Class<? extends Throwable> exType : exclude) {
            if (exType.isAssignableFrom(ex.getClass())) {
                return false;
            }
        }
        if (include.length == 0)
            return true;
        for (Class<? extends Throwable> exType : include) {
            if (exType.isAssignableFrom(ex.getClass())) {
                return true;
            }
        }
        return false;
    }
}
