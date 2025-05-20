package ar.com.acme.commons.logging;

import ch.qos.logback.core.PropertyDefinerBase;

public class LogbackPathHelper extends PropertyDefinerBase {
    @Override
    public String getPropertyValue() {
        return System.getenv("LOGGING_HOME");
    }
}
