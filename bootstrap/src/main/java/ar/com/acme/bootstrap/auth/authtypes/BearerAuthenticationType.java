package ar.com.acme.bootstrap.auth.authtypes;

import java.util.UUID;
import org.springframework.stereotype.Service;

import ar.com.acme.adapter.principal.PrincipalService;
import ar.com.acme.bootstrap.auth.support.AuthenticationToken;
import ar.com.acme.bootstrap.errors.AuthException;
import ar.com.acme.commons.Constants;
import ar.com.acme.bootstrap.jws.IJwsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service(Constants.SYS_CAD_HTTPAUTH_BEARER)
@RequiredArgsConstructor
public class BearerAuthenticationType implements IAuthenticationType {
        private final PrincipalService principalService;
        private final IJwsService jwsService;

        @Override
        public AuthenticationToken generateAuthentication(HttpServletRequest request, String authcad) {
            jwsService.validateJws(authcad);

            var token = jwsService.getIdFromJws(authcad);

            var repoUser = principalService.findByToken(UUID.fromString(token))
                                           .orElseThrow(() -> new AuthException(Constants.MSJ_SES_ERR_NOACTIVETOKEN));

            var authorities = jwsService.getAuthoritiesFromJws(authcad);

            return new AuthenticationToken(repoUser, authorities);
        }
}
