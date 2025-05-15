package ar.com.acme.application.user.usecases.setactive;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ar.com.acme.commons.Constants;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(Constants.URL_CONTROLLER_USER)
@RequiredArgsConstructor
public class UserSetActiveController {
    private final IUserSetActiveUseCase setActiveUseCase;

    @PutMapping("/{userId}/active/{active}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void addPhone(@PathVariable("userId") UUID userId, @PathVariable("active") boolean active) {
        setActiveUseCase.setActive(userId, active);
    }
}
