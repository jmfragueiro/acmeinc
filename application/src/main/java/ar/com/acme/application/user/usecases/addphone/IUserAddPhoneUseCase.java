package ar.com.acme.application.user.usecases.addphone;

import java.util.UUID;

import ar.com.acme.model.phone.Phone;
import ar.com.acme.application.phone.mappers.PhoneInMapper;

public interface IUserAddPhoneUseCase {
    public Phone addPhone(UUID userId, PhoneInMapper wiPhone);
}
