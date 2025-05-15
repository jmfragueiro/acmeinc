package ar.com.acme.application.user.usecases.setactive;

import java.util.UUID;

import org.springframework.stereotype.Service;

import ar.com.acme.model.base.repository.ItemNotFoundException;
import ar.com.acme.application.user.IUserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSetActiveUseCase implements IUserSetActiveUseCase {
    private final IUserService userService;

    public void setActive(UUID userId, boolean active) {
        var user = userService.findById(userId).orElseThrow(() -> new ItemNotFoundException(userId.toString()));

        user.setActive(active);

        userService.persist(user);
    }
}
