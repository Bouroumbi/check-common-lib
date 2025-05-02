package com.tonorganisation.checkcommonlib.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "check-common")
public class ExecutionTimeProperties {
    private boolean logExecutionTime = true;

    public boolean isLogExecutionTime() {
        return logExecutionTime;
    }

    public void setLogExecutionTime(boolean logExecutionTime) {
        this.logExecutionTime = logExecutionTime;
    }
}