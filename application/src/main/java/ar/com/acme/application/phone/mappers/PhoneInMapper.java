package ar.com.acme.application.phone.mappers;

import java.util.UUID;

import ar.com.acme.application.phone.IPhoneService;
import ar.com.acme.model.phone.Phone;
import ar.com.acme.model.phone.errors.PhoneException;
import ar.com.acme.commons.Constants;

public record PhoneInMapper(UUID id, Long number, Integer citycode, Integer countrycode) {
    public Phone toPhone(IPhoneService phoneService) {
        Phone phone = new Phone();

        if (this.id != null) {
            phone = phoneService.findAliveById(this.id())
                                .orElseThrow(() -> new PhoneException(Constants.MSJ_REP_ERR_NOITEM, Phone.ENTITY_NAME));
        } else {
            phone = new Phone();
        }

        phone.setNumber(this.number);
        phone.setCitycode(this.citycode);
        phone.setCountrycode(this.countrycode);

        return phone;
    }
 }
