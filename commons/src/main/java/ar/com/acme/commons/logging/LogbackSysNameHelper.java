package ar.com.acme.commons.logging;

import ch.qos.logback.core.PropertyDefinerBase;

public class LogbackSysNameHelper extends PropertyDefinerBase {
    @Override
    public String getPropertyValue() {
        return "ACMEBACKEND";
    }
}
