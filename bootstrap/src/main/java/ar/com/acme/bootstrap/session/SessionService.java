package ar.com.acme.bootstrap.session;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import ar.com.acme.commons.Constants;
import ar.com.acme.commons.Properties;
import ar.com.acme.adapter.principal.Principal;
import ar.com.acme.adapter.principal.PrincipalService;
import ar.com.acme.bootstrap.errors.AuthException;
import ar.com.acme.bootstrap.jws.IJwsService;

@Service
@RequiredArgsConstructor
public class SessionService implements ISessionService {
        private final PrincipalService principalService;
        private final IJwsService jwsService;
        private final Properties properties;

    @Override
    public String login(Authentication authentication) {
        var principal = getPrincipalFromAuthentication(authentication);

        validateCanCreateSession(principal);

        principal.setToken(UUID.randomUUID());
        principal.setLastLogin(LocalDateTime.now());

        var token = jwsService.generateJws(principal);

        principalService.updatePrincipal(principal);

        return token;
    }

    @Override
    public String logout(Authentication authentication) {
        var principal = getPrincipalFromAuthentication(authentication);

        validateCanDeleteSession(principal);

        principal.setToken(null);

        principalService.updatePrincipal(principal);

        return principal.getName();
    }

    private Principal getPrincipalFromAuthentication(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthException(Constants.MSJ_SES_ERR_USERNOAUTH);
        }

        var principal = (Principal)authentication.getPrincipal();

        if (principal == null) {
            throw new AuthException(Constants.MSJ_SES_ERR_ONAUTH);
        }

        return principal;
    }

    private void validateCanCreateSession(Principal principal) {
        // permitirá o no multiples sesiones de usuario segun la propiedad
        // application.security.user_multisession en application.properties
        if (principal.getToken() != null && !properties.getSecurity().get("user_multisession").equalsIgnoreCase("true")) {
            throw new AuthException(Constants.MSJ_SES_ERR_USERALREADYLOGGED);
        }

        if (!principal.canOperate()) {
            throw new AuthException(Constants.MSJ_SES_ERR_USERCANTOP);
        };
    }

    private void validateCanDeleteSession(Principal principal) {
        if (principal.getToken() == null) {
            throw new AuthException(Constants.MSJ_SES_ERR_USERNOTLOGGED);
        }
    }
}
