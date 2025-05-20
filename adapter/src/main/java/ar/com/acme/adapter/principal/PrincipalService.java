package ar.com.acme.adapter.principal;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ar.com.acme.application.password.IPasswordService;
import ar.com.acme.application.user.IUserService;
import ar.com.acme.model.user.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalService implements IPrincipalService<IPrincipal> {
    private final IUserService userService;
    private final IPasswordService passwordService;

    @Override
    public Optional<IPrincipal> findByName(String name) {
        return getPrincipal(userService.findByName(name));
    }

    @Override
    public Optional<IPrincipal> findByToken(UUID token) {
        return getPrincipal(userService.findByToken(token));
    }

    @Override
    public void updatePrincipal(IPrincipal principal) {
        userService.persist((User)principal.getUser());
    }

    private Optional<IPrincipal> getPrincipal(Optional<User> user) {
        if (user.isPresent()) {
            return Optional.of(new Principal(user.get(), passwordService, getAuthorities(user.get())));
        } else {
            return Optional.empty();
        }
    }

    private Collection<String> getAuthorities(User user) {
        // retornar los roles asociados al principal
        return Collections.singleton("ROLE_ADMIN");
    }
}
