package ar.com.acme.application.user;

import java.util.Optional;
import java.util.UUID;

import ar.com.acme.model.phone.Phone;
import ar.com.acme.model.user.User;
import ar.com.acme.application.templates.service.IService;
import ar.com.acme.commons.principal.IPrincipalUserService;

public interface IUserService extends IService<User, UUID>, IPrincipalUserService<User, UUID> {
    Optional<User> findByName(String name);

    Optional<User> findByToken(UUID token);

    Optional<User> findByEmail(String email);

    Boolean isValidEmail(String email);

    Boolean isValidPassword(String rawPassword);

    String encodePassword(CharSequence rawPassword);

    Phone addPhone(User user,Phone phone);
}
