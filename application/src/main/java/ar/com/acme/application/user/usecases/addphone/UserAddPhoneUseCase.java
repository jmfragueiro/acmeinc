package ar.com.acme.application.user.usecases.addphone;

import java.util.UUID;

import org.springframework.stereotype.Service;

import ar.com.acme.application.phone.IPhoneService;
import ar.com.acme.model.phone.Phone;
import ar.com.acme.application.phone.mappers.PhoneInMapper;
import ar.com.acme.model.base.repository.ItemNotFoundException;
import ar.com.acme.application.user.IUserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAddPhoneUseCase implements IUserAddPhoneUseCase {
    private final IUserService userService;
    private final IPhoneService phoneService;

    public Phone addPhone(UUID userId, PhoneInMapper wiPhone) {
        var user = userService.findAliveById(userId).orElseThrow(() -> new ItemNotFoundException(userId.toString()));
        var phone = userService.addPhone(user, wiPhone.toPhone(phoneService));

        phoneService.persist(phone);
        userService.persist(user);

        return phone;
    }
}
