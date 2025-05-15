package ar.com.acme.model.base.entity;

import ar.com.acme.commons.MessageException;

public class EntityException extends MessageException {
    public EntityException(String mensaje) {
        super(mensaje);
    }

    public EntityException(String mensaje, String extra) {
        super(mensaje, extra);
    }
}
