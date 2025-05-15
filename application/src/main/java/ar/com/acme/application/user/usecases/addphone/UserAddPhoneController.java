package ar.com.acme.application.user.usecases.addphone;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ar.com.acme.application.phone.mappers.PhoneInMapper;
import ar.com.acme.application.phone.mappers.PhoneOutMapper;
import ar.com.acme.commons.Constants;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(Constants.URL_CONTROLLER_USER)
@RequiredArgsConstructor
public class UserAddPhoneController {
    private final IUserAddPhoneUseCase addPhoneUseCase;

    @PostMapping("/{userId}/phones")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public PhoneOutMapper addPhone(@PathVariable("userId") UUID userId, @RequestBody PhoneInMapper wiPhone) {
        return PhoneOutMapper.fromPhone(addPhoneUseCase.addPhone(userId, wiPhone));
    }
}
