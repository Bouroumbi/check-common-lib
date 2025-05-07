package com.tonorganisation.checkcommonlib.aspect;

import com.tonorganisation.checkcommonlib.annotation.RetryOnFailure;
import com.tonorganisation.checkcommonlib.properties.RetryProperties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Aspect qui intercepte les méthodes annotées avec {@link RetryOnFailure}
 * et applique une logique de re-tentative en cas d'échec.
 *
 * <p>
 * Le comportement est configurable via l'annotation ou, par défaut,
 * via les propriétés {@link RetryProperties}.
 * </p>
 * <p>
 * Cette approche permet de rendre les méthodes plus résilientes en cas d’échecs
 * transitoires
 * (ex : appels réseau, base de données temporairement indisponible).
 * </p>
 * <p>
 * Exemple d'utilisation :
 * </p>
 * 
 * <pre>
 * {@code
 * @RetryOnFailure(maxAttempts = 5, delayMs = 1000, include = { IOException.class })
 * public void appelerService() {
 *     // logique instable
 * }
 * }
 * </pre>
 *
 * <p>
 * Lorsqu’une exception est levée, l’aspect vérifie si elle est incluse ou
 * exclue,
 * puis attend un délai entre chaque tentative avant de réessayer.
 * </p>
 * 
 * @see RetryOnFailure
 * @see RetryProperties
 */
@Aspect
@Slf4j
public class RetryAspect {

    /**
     * Propriétés globales de configuration du comportement de re-tentative.
     */
    private final RetryProperties retryProperties;

    /**
     * Constructeur avec injection des propriétés.
     *
     * @param retryProperties propriétés contenant les valeurs par défaut de
     *                        re-tentative
     */
    public RetryAspect(RetryProperties retryProperties) {
        this.retryProperties = retryProperties;
    }

    /**
     * Intercepte les méthodes annotées avec {@link RetryOnFailure} et exécute la
     * logique de re-tentative.
     *
     * @param joinPoint       le point de jonction représentant la méthode
     *                        interceptée
     * @param retryAnnotation l'annotation {@link RetryOnFailure} présente sur la
     *                        méthode
     * @return le résultat de la méthode si elle réussit
     * @throws Throwable l'exception finale si toutes les tentatives échouent ou si
     *                   l'exception est exclue
     */
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

    /**
     * Détermine si l’exception levée mérite une re-tentative selon les types inclus
     * et exclus.
     *
     * @param ex      l’exception levée
     * @param include types d'exceptions à inclure pour le retry
     * @param exclude types d'exceptions à exclure du retry
     * @return {@code true} si la méthode doit être retentée, {@code false} sinon
     */
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
