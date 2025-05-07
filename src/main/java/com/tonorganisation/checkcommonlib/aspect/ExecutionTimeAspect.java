package com.tonorganisation.checkcommonlib.aspect;

import com.tonorganisation.checkcommonlib.properties.ExecutionTimeProperties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Aspect utilisé pour mesurer et journaliser le temps d'exécution des méthodes
 * publiques
 * en dehors du package {@code check-common-lib}.
 *
 * <p>
 * Cet aspect est activé uniquement si la propriété
 * {@code check-common.log-execution-time=true}
 * est présente dans la configuration Spring.
 * </p>
 * <p>
 * Il entoure chaque appel de méthode interceptée pour mesurer le temps entre
 * l'entrée et la sortie,
 * et loggue le résultat via SLF4J.
 * </p>
 * <p>
 * Exemple de log :
 * {@code [EXECUTION TIME] MyService.doWork(..) exécutée en 105 ms}
 * </p>
 * <p>
 * Ce mécanisme permet d’identifier rapidement les points de lenteur dans
 * l'application.
 * </p>
 * 
 * @author Bouroumbi
 */
@Slf4j
@Aspect
@Component
@ConditionalOnProperty(name = "check-common.log-execution-time", havingValue = "true")
public class ExecutionTimeAspect {

    /**
     * Propriétés de configuration associées à la mesure du temps d'exécution.
     */
    private final ExecutionTimeProperties executionTimeProperties;

    /**
     * Constructeur avec injection des propriétés d'exécution.
     *
     * @param executionTimeProperties propriétés personnalisées liées à la mesure de
     *                                temps
     */
    public ExecutionTimeAspect(ExecutionTimeProperties executionTimeProperties) {
        this.executionTimeProperties = executionTimeProperties;
    }

    /**
     * Intercepte l'exécution de toutes les méthodes publiques (hors librairie
     * {@code check-common-lib}),
     * mesure la durée d'exécution, puis loggue le temps total.
     *
     * @param joinPoint le point de jonction AOP
     * @return le résultat de la méthode interceptée
     * @throws Throwable en cas d'erreur pendant l'exécution de la méthode
     */
    @Around("execution(public * *(..)) && !within(com.tonorganisation.checkcommonlib..*)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();
        long duration = end - start;

        String methodName = joinPoint.getSignature().toShortString();
        log.info("[EXECUTION TIME] {} exécutée en {} ms", methodName, duration);

        return result;
    }
}
