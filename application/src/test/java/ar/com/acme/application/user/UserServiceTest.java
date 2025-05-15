package ar.com.acme.application.user;

import ar.com.acme.application.email.IEmailServiceValidator;
import ar.com.acme.application.password.IPasswordService;
import ar.com.acme.application.phone.IPhoneService;
import ar.com.acme.model.phone.Phone;
import ar.com.acme.model.user.IUserRepo;
import ar.com.acme.model.user.User;
import ar.com.acme.model.user.errors.UserException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    private IUserRepo userRepo;
    private IPhoneService phoneService;
    private IPasswordService passwordService;
    private IEmailServiceValidator emailService;
    private UserService userService;
    private User user;
    private Phone phone;

    @BeforeEach
    void setUp() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        userRepo = mock(IUserRepo.class);
        phoneService = mock(IPhoneService.class);
        passwordService = mock(IPasswordService.class);
        emailService = mock(IEmailServiceValidator.class);
        userService = new UserService(userRepo, phoneService, passwordService, emailService);

        user = new User("fito","test@acme.cl","Hunter2@3!",true,null,null,new HashSet<Phone>());
        var userClazz = user.getClass();
        var userIdField = userClazz.getSuperclass().getDeclaredField("id");
        userIdField.setAccessible(true);
        userIdField.set(user, UUID.randomUUID());

        phone = new Phone(null, 132456L, 376, 54);
        var phoneClazz = phone.getClass();
        var phoneIdField = phoneClazz.getSuperclass().getDeclaredField("id");
        phoneIdField.setAccessible(true);
        phoneIdField.set(phone, UUID.randomUUID());
    }

    @Test
    void addPhone_shouldAddPhoneToUser() {
        Phone result = userService.addPhone(user, phone);

        assertEquals(phone, result);
        assertTrue(user.getPhones().contains(phone));
    }

    @Test
    void addPhone_shouldRevivePhoneOnAddPhonetoUser() {
        phone.kill();

        Phone result = userService.addPhone(user, phone);

        assertTrue(result.isAlive());
    }

    @Test
    void addPhone_shouldNotAddNullPhoneToUser() {
        Phone phone = null;

        assertThrows(UserException.class, () -> userService.addPhone(user, phone));
    }

    @Test
    void addPhone_shouldNotAddPhoneToNulllUser() {
        User user = null;

        assertThrows(UserException.class, () -> userService.addPhone(user, phone));
    }
}