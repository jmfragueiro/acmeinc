package ar.com.acme.model.phone.errors;

import ar.com.acme.commons.Constants;
import ar.com.acme.commons.MessageException;

public class PhoneException extends MessageException {
    public PhoneException(String name, String message) {
        super(message.concat(Constants.SYS_CAD_FORMATTER_STRING).formatted(name));
    }
}
