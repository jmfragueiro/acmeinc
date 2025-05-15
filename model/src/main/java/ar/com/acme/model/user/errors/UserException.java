package ar.com.acme.model.user.errors;

import ar.com.acme.commons.Constants;
import ar.com.acme.commons.MessageException;

public class UserException extends MessageException {
    public UserException(String message, String name) {
        super(message.concat(Constants.SYS_CAD_FORMATTER_STRING).formatted(name));
    }
}
