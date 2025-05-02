package com.tonorganisation.checkcommonlib.config;

import com.tonorganisation.checkcommonlib.aspect.AuditAspect;
import com.tonorganisation.checkcommonlib.aspect.ExecutionTimeAspect;
import com.tonorganisation.checkcommonlib.aspect.RetryAspect;
import com.tonorganisation.checkcommonlib.properties.AuditProperties;
import com.tonorganisation.checkcommonlib.properties.ExecutionTimeProperties;
import com.tonorganisation.checkcommonlib.properties.RetryProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        AuditProperties.class,
        ExecutionTimeProperties.class,
        RetryProperties.class
})
public class CheckCommonAutoConfiguration {

    @Bean
    @ConditionalOnProperty(name = "check-common.audit-enabled", havingValue = "true", matchIfMissing = true)
    public AuditAspect auditAspect(AuditProperties auditProperties) {
        return new AuditAspect(auditProperties);
    }

    @Bean
    @ConditionalOnProperty(name = "check-common.log-execution-time", havingValue = "true", matchIfMissing = true)
    public ExecutionTimeAspect executionTimeAspect(ExecutionTimeProperties executionTimeProperties) {
        return new ExecutionTimeAspect(executionTimeProperties);
    }

    @Bean
    @ConditionalOnProperty(name = "check-common.retry.enabled", havingValue = "true", matchIfMissing = true)
    public RetryAspect retryAspect(RetryProperties retryProperties) {
        return new RetryAspect(retryProperties);
    }
}
