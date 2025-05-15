package ar.com.acme.application.phone.mappers;

import java.util.UUID;

import ar.com.acme.model.phone.Phone;

public record PhoneOutMapper(UUID id, Long number, Integer citycode, Integer countrycode) {
    public static PhoneOutMapper fromPhone(Phone phone) {
        return new PhoneOutMapper(phone.getId(), phone.getNumber(), phone.getCitycode(), phone.getCountrycode());
    }
 }
