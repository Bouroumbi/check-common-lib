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

/**
 * Configuration automatique de la librairie check-common-lib.
 *
 * <p>
 * Cette classe déclare les beans des aspects AOP conditionnellement à la
 * présence de
 * certaines propriétés dans le fichier de configuration (`application.yml` ou
 * `application.properties`).
 * </p>
 *
 * <p>
 * Les aspects suivants sont supportés :
 * </p>
 * <ul>
 * <li>{@link AuditAspect} - journalisation des entrées/sorties/exceptions</li>
 * <li>{@link ExecutionTimeAspect} - mesure du temps d'exécution des
 * méthodes</li>
 * <li>{@link RetryAspect} - re-tentatives automatiques en cas d'échec</li>
 * </ul>
 * 
 *
 * <p>
 * Ces fonctionnalités sont activables ou désactivables via des propriétés :
 * </p>
 * 
 * <pre>
 * check-common:
 *   audit-enabled: true
 *   log-execution-time: true
 *   retry:
 *     enabled: true
 * </pre>
 * 
 *
 * <p>
 * Grâce à l'annotation {@link EnableConfigurationProperties}, les propriétés
 * personnalisées
 * {@link AuditProperties}, {@link ExecutionTimeProperties} et
 * {@link RetryProperties}
 * sont automatiquement chargées et injectées dans les aspects.
 * </p>
 *
 * @see AuditAspect
 * @see ExecutionTimeAspect
 * @see RetryAspect
 * @see AuditProperties
 * @see ExecutionTimeProperties
 * @see RetryProperties
 */
@Configuration
@EnableConfigurationProperties({
        AuditProperties.class,
        ExecutionTimeProperties.class,
        RetryProperties.class
})
public class CheckCommonAutoConfiguration {

    /**
     * Bean conditionnel pour l'aspect d'audit (log des appels de méthodes).
     */
    @Bean
    @ConditionalOnProperty(name = "check-common.audit-enabled", havingValue = "true", matchIfMissing = true)
    public AuditAspect auditAspect(AuditProperties auditProperties) {
        return new AuditAspect(auditProperties);
    }

    /**
     * Bean conditionnel pour l'aspect de mesure du temps d'exécution.
     */
    @Bean
    @ConditionalOnProperty(name = "check-common.log-execution-time", havingValue = "true", matchIfMissing = true)
    public ExecutionTimeAspect executionTimeAspect(ExecutionTimeProperties executionTimeProperties) {
        return new ExecutionTimeAspect(executionTimeProperties);
    }

    /**
     * Bean conditionnel pour l'aspect de retry automatique.
     */
    @Bean
    @ConditionalOnProperty(name = "check-common.retry.enabled", havingValue = "true", matchIfMissing = true)
    public RetryAspect retryAspect(RetryProperties retryProperties) {
        return new RetryAspect(retryProperties);
    }
}
