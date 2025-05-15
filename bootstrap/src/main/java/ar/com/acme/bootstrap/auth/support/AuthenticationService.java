package ar.com.acme.bootstrap.auth.support;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import ar.com.acme.adapter.principal.IPrincipal;
import ar.com.acme.bootstrap.auth.types.IAuthenticationType;
import ar.com.acme.bootstrap.errors.AuthException;
import ar.com.acme.commons.Constants;
import ar.com.acme.bootstrap.http.HttpRequestAuthorizationHeader;
import ar.com.acme.bootstrap.session.ISessionService;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {
    private final Map<String, IAuthenticationType> authTypesMap;
    private final ISessionService sessionService;

    @Override
    public String authenticateAndLogin(HttpServletRequest request) throws AuthenticationException {
            return sessionService.login(this.authenticateFromRequest(request));
    }

    @Override
    public String authenticateAndLogout(HttpServletRequest request) throws AuthenticationException {
            return sessionService.logout(this.authenticateFromRequest(request));
    }

    @Override
    public Authentication authenticateFromRequest(HttpServletRequest request) throws AuthenticationException {
        var authHeader = HttpRequestAuthorizationHeader.from(request);

        IAuthenticationType authType =
            Optional.of(authTypesMap.get(authHeader.type()))
                                    .orElseThrow(() -> new AuthException(Constants.MSJ_REQ_ERR_BADREQUEST));

        return authenticate(authType.generateAuthentication(request, authHeader.value()));
    }

    @Override
    public Authentication authenticate(Authentication auth) {
        var principal = (IPrincipal)auth.getPrincipal();

        if (principal == null) {
            throw new AuthException(Constants.MSJ_SES_ERR_INVALIDTOKEN);
        }

        if (auth.getCredentials() != null && !principal.matchCredential(auth.getCredentials().toString())) {
            throw new AuthException(Constants.MSJ_SES_ERR_BADCREDENTIAL);
        }

        auth.setAuthenticated(true);

        return auth;
    }
}