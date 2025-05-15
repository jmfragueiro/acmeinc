package ar.com.acme.application.user.usecases.setactive;

import ar.com.acme.model.base.repository.ItemNotFoundException;
import ar.com.acme.application.user.IUserService;
import ar.com.acme.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserSetActiveUseCaseTest {
    private IUserService userService;
    private UserSetActiveUseCase useCase;

    @BeforeEach
    void setUp() {
        userService = mock(IUserService.class);
        useCase = new UserSetActiveUseCase(userService);
    }

    @Test
    void setActive_shouldSetActiveAndPersist_whenUserExists() {
        UUID userId = UUID.randomUUID();
        User user = mock(User.class);

        when(userService.findById(userId)).thenReturn(Optional.of(user));

        useCase.setActive(userId, true);

        verify(user).setActive(true);
        verify(userService).persist(user);
    }

    @Test
    void setActive_shouldThrowException_whenUserNotFound() {
        UUID userId = UUID.randomUUID();

        when(userService.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> useCase.setActive(userId, false));
        verify(userService, never()).persist(any());
    }
}