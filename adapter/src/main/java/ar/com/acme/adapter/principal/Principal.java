package ar.com.acme.adapter.principal;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

import ar.com.acme.application.password.IPasswordService;
import ar.com.acme.model.user.User;
import ar.com.acme.commons.Constants;
import ar.com.acme.commons.Logging;

public class Principal implements IPrincipal {
    private final User user;
    private final IPasswordService passwordService;
    private final Collection<String> authorities;

    public Principal(User user, IPasswordService passwordService, Collection<String> authorities) {
        this.user = user;
        this.passwordService = passwordService;
        this.authorities = authorities;
    }

    @Override
    public User getUser() {
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
        if (!user.isAlive()) {
            Logging.info(getClass(), Constants.MSJ_USR_ERR_DELETED);
            return false;
        }

        if (!user.getActive()) {
            Logging.info(getClass(), Constants.MSJ_USR_ERR_USERINACTIVE);
            return false;
        }

        return true;
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
