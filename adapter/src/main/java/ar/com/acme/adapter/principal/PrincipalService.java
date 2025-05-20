package ar.com.acme.adapter.principal;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ar.com.acme.application.password.IPasswordService;
import ar.com.acme.application.user.IUserService;
import ar.com.acme.model.base.principal.IPrincipalUser;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalService implements IPrincipalService<Principal, UUID> {
    private final IUserService userService;
    private final IPasswordService passwordService;

    @Override
    public Optional<Principal> findByName(String name) {
        var user = userService.findByName(name);

        if (user.isPresent()) {
            return Optional.of(new Principal(user.get(), passwordService, getAuthorities(user.get())));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Principal> findByToken(UUID token) {
        var user = userService.findByToken(token);

        if (user.isPresent()) {
            return Optional.of(new Principal(user.get(), passwordService, getAuthorities(user.get())));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void updatePrincipal(Principal principal) {
        userService.update(principal.getUser());
    }

    private Collection<String> getAuthorities(IPrincipalUser<UUID> user) {
        // retornar los roles asociados al principal
        return Collections.singleton("ROLE_ADMIN");
    }
}
