package com.tonorganisation.checkcommonlib.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Propriétés de configuration pour la logique de réessai automatique des
 * méthodes.
 *
 * <p>
 * Ces propriétés sont chargées depuis les fichiers de configuration de
 * l'application
 * (par exemple, <code>application.yml</code> ou
 * <code>application.properties</code>)
 * sous le préfixe <code>check-common.retry</code>.
 * </p>
 *
 * <p>
 * Exemple de configuration :
 * </p>
 * 
 * <pre>
 * check-common:
 *   retry:
 *     enabled: true
 *     default-max-attempts: 5
 *     default-delay-ms: 1000
 * </pre>
 * 
 *
 * @see com.tonorganisation.checkcommonlib.aspect.RetryAspect
 */
@ConfigurationProperties(prefix = "check-common.retry")
public class RetryProperties {

    /**
     * Indique si la logique de réessai est activée ou non.
     * Par défaut, elle est activée ({@code true}).
     */
    private boolean enabled = true;

    /**
     * Le nombre maximal de tentatives de réessai.
     * Par défaut, il est défini à 3.
     */
    private int defaultMaxAttempts = 3;

    /**
     * Le délai entre chaque tentative de réessai, en millisecondes.
     * Par défaut, il est défini à 500 ms.
     */
    private long defaultDelayMs = 500;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getDefaultMaxAttempts() {
        return defaultMaxAttempts;
    }

    public void setDefaultMaxAttempts(int defaultMaxAttempts) {
        this.defaultMaxAttempts = defaultMaxAttempts;
    }

    public long getDefaultDelayMs() {
        return defaultDelayMs;
    }

    public void setDefaultDelayMs(long defaultDelayMs) {
        this.defaultDelayMs = defaultDelayMs;
    }
}
