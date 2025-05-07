package com.tonorganisation.checkcommonlib.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Propriétés de configuration pour l'aspect d'audit.
 *
 * <p>
 * Ces propriétés sont chargées à partir du fichier de configuration (ex:
 * `application.yml` ou `application.properties`)
 * sous le préfixe <code>check-common</code>.
 * </p>
 *
 * <p>
 * Exemple de configuration :
 * </p>
 * 
 * <pre>
 * check-common:
 *   audit-enabled: true
 * </pre>
 * 
 *
 * @see com.tonorganisation.checkcommonlib.aspect.AuditAspect
 */
@ConfigurationProperties(prefix = "check-common")
public class AuditProperties {

    /**
     * Active ou désactive l'aspect d'audit.
     * Par défaut à {@code true}.
     */
    private boolean auditEnabled = true;

    public boolean isAuditEnabled() {
        return auditEnabled;
    }

    public void setAuditEnabled(boolean auditEnabled) {
        this.auditEnabled = auditEnabled;
    }
}
