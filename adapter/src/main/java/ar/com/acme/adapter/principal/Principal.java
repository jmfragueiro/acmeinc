package ar.com.acme.adapter.principal;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

import ar.com.acme.application.password.IPasswordService;
import ar.com.acme.commons.principal.IPrincipal;
import ar.com.acme.commons.principal.IPrincipalUser;

public class Principal implements IPrincipal<UUID> {
    private final IPrincipalUser<UUID> user;
    private final IPasswordService passwordService;
    private final Collection<String> authorities;

    public Principal(IPrincipalUser<UUID> user, IPasswordService passwordService, Collection<String> authorities) {
        this.user = user;
        this.passwordService = passwordService;
        this.authorities = authorities;
    }

    @Override
    public IPrincipalUser<UUID> getUser() {
        return user;
    }

    @Override
    public String getName() {
        return user.getName();
    }

    @Override
    public void setToken(UUID token) {
        user.setToken(token);
    }

    @Override
    public UUID getToken() {
        return user.getToken();
    }

    @Override
    public void setLastLogin(LocalDateTime lastLogin) {
        user.setLastLogin(lastLogin);
    }

    @Override
    public LocalDateTime getLastLogin() {
        return user.getLastLogin();
    }

    @Override
    public boolean canOperate() {
        return user.canOperate();
    }

    @Override
    public boolean matchCredential(String rawPassword) {
        return passwordService.matches(rawPassword, user.getPassword());
    }

    @Override
    public Collection<String> getAuthorities() {
        return authorities;
    }
}
