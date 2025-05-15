package ar.com.acme.model.phone.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ar.com.acme.commons.ResponseError;

@RestControllerAdvice
public class PhoneErrorHandler {
    @ExceptionHandler(PhoneException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public final ResponseError UserExceptionHandler(PhoneException ex) {
        return new ResponseError(ex.getMessage());
    }
}
