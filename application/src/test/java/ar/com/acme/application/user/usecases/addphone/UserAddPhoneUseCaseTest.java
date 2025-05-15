package ar.com.acme.application.user.usecases.addphone;

import ar.com.acme.application.phone.IPhoneService;
import ar.com.acme.model.phone.Phone;
import ar.com.acme.application.phone.mappers.PhoneInMapper;
import ar.com.acme.model.base.repository.ItemNotFoundException;
import ar.com.acme.application.user.IUserService;
import ar.com.acme.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserAddPhoneUseCaseTest {
    private IUserService userService;
    private IPhoneService phoneService;
    private UserAddPhoneUseCase useCase;

    @BeforeEach
    void setUp() {
        userService = mock(IUserService.class);
        phoneService = mock(IPhoneService.class);
        useCase = new UserAddPhoneUseCase(userService, phoneService);
    }

    @Test
    void addPhone_shouldAddPhoneAndPersist_whenUserExists() {
        UUID userId = UUID.randomUUID();
        PhoneInMapper wiPhone = mock(PhoneInMapper.class);
        User user = mock(User.class);
        Phone phone = mock(Phone.class);

        when(userService.findAliveById(userId)).thenReturn(Optional.of(user));
        when(wiPhone.toPhone(phoneService)).thenReturn(phone);
        when(userService.addPhone(user, phone)).thenReturn(phone);

        Phone result = useCase.addPhone(userId, wiPhone);

        assertEquals(phone, result);
        verify(phoneService).persist(phone);
        verify(userService).persist(user);
    }

    @Test
    void addPhone_shouldThrowException_whenUserNotFound() {
        UUID userId = UUID.randomUUID();
        PhoneInMapper wiPhone = mock(PhoneInMapper.class);

        when(userService.findAliveById(userId)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> useCase.addPhone(userId, wiPhone));
        verify(phoneService, never()).persist(any());
        verify(userService, never()).persist(any());
    }
}