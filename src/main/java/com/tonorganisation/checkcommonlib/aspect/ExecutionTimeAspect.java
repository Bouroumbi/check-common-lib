package com.tonorganisation.checkcommonlib.aspect;

import com.tonorganisation.checkcommonlib.properties.ExecutionTimeProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@ConditionalOnProperty(name = "check-common.log-execution-time", havingValue = "true")
public class ExecutionTimeAspect {
    private final ExecutionTimeProperties executionTimeProperties;

    public ExecutionTimeAspect(ExecutionTimeProperties executionTimeProperties) {
        this.executionTimeProperties = executionTimeProperties;
    }

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