package com.tonorganisation.checkcommonlib.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "check-common.retry")
public class RetryProperties {
    private boolean enabled = true;
    private int defaultMaxAttempts = 3;
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
