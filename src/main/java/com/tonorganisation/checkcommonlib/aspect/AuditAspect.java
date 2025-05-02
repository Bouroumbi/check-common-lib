package com.tonorganisation.checkcommonlib.aspect;

import com.tonorganisation.checkcommonlib.properties.AuditProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "check-common.audit-enabled", havingValue = "true")
public class AuditAspect {

    private final AuditProperties auditProperties;

    @Before("execution(public * *(..)) && !within(com.tonorganisation.checkcommonlib..*)")
    public void logMethodEntry(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        log.info("[AUDIT] Entrée dans : {} avec paramètres : {}", methodName, Arrays.toString(args));
    }

    @AfterReturning(pointcut = "execution(public * *(..)) && !within(com.tonorganisation.checkcommonlib..*)", returning = "result")
    public void logMethodExit(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().toShortString();
        log.info("[AUDIT] Sortie de : {} avec résultat : {}", methodName, result);
    }

    @AfterThrowing(pointcut = "execution(public * *(..)) && !within(com.tonorganisation.checkcommonlib..*)", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().toShortString();
        log.error("[AUDIT] Exception dans : {} -> {}", methodName, ex.getMessage(), ex);
    }
}