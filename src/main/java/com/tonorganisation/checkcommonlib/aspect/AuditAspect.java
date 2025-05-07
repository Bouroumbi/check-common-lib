package com.tonorganisation.checkcommonlib.aspect;

import com.tonorganisation.checkcommonlib.properties.AuditProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Aspect d'audit utilisé pour tracer automatiquement l'exécution des méthodes
 * publiques
 * dans les composants applicatifs, en dehors de la librairie
 * {@code check-common-lib}.
 *
 * <p>
 * Cet aspect se déclenche uniquement lorsque la propriété
 * {@code check-common.audit-enabled=true}
 * est définie dans la configuration Spring.
 * </p>
 * <p>
 * Les événements suivants sont interceptés :
 * </p>
 * <ul>
 * <li>Entrée dans une méthode avec les paramètres</li>
 * <li>Sortie de la méthode avec le résultat retourné</li>
 * <li>Exception levée par la méthode</li>
 * </ul>
 *
 * <p>
 * L’objectif est de faciliter le suivi du comportement de l’application sans
 * modifier le code métier.
 * </p>
 * 
 * @author Bouroumbi
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "check-common.audit-enabled", havingValue = "true")
public class AuditAspect {

    /**
     * Propriétés d'audit définies par l'utilisateur.
     */
    private final AuditProperties auditProperties;

    /**
     * Intercepte l'entrée dans toutes les méthodes publiques (hors librairie
     * {@code check-common-lib}),
     * et loggue le nom de la méthode ainsi que ses arguments.
     *
     * @param joinPoint le point d'exécution intercepté
     */
    @Before("execution(public * *(..)) && !within(com.tonorganisation.checkcommonlib..*)")
    public void logMethodEntry(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        log.info("[AUDIT] Entrée dans : {} avec paramètres : {}", methodName, Arrays.toString(args));
    }

    /**
     * Intercepte la sortie d'une méthode publique (hors librairie
     * {@code check-common-lib})
     * et loggue la valeur de retour.
     *
     * @param joinPoint le point d'exécution intercepté
     * @param result    l'objet retourné par la méthode
     */
    @AfterReturning(pointcut = "execution(public * *(..)) && !within(com.tonorganisation.checkcommonlib..*)", returning = "result")
    public void logMethodExit(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().toShortString();
        log.info("[AUDIT] Sortie de : {} avec résultat : {}", methodName, result);
    }

    /**
     * Intercepte une exception levée par une méthode publique (hors librairie
     * {@code check-common-lib})
     * et loggue l'exception.
     *
     * @param joinPoint le point d'exécution intercepté
     * @param ex        l'exception levée
     */
    @AfterThrowing(pointcut = "execution(public * *(..)) && !within(com.tonorganisation.checkcommonlib..*)", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().toShortString();
        log.error("[AUDIT] Exception dans : {} -> {}", methodName, ex.getMessage(), ex);
    }
}
