package com.tonorganisation.checkcommonlib.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Propriétés de configuration pour l'aspect de mesure du temps d'exécution.
 *
 * <p>
 * Ces propriétés sont chargées depuis les fichiers de configuration de
 * l'application
 * (par exemple, <code>application.yml</code> ou
 * <code>application.properties</code>)
 * sous le préfixe <code>check-common</code>.
 * </p>
 *
 * <p>
 * Exemple de configuration :
 * </p>
 * 
 * <pre>
 * check-common:
 *   log-execution-time: true
 * </pre>
 * 
 *
 * @see com.tonorganisation.checkcommonlib.aspect.ExecutionTimeAspect
 */
@ConfigurationProperties(prefix = "check-common")
public class ExecutionTimeProperties {

    /**
     * Indique si la durée d'exécution des méthodes doit être loggée.
     * Activé par défaut ({@code true}).
     */
    private boolean logExecutionTime = true;

    public boolean isLogExecutionTime() {
        return logExecutionTime;
    }

    public void setLogExecutionTime(boolean logExecutionTime) {
        this.logExecutionTime = logExecutionTime;
    }
}
