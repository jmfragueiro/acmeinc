package ar.com.acme.bootstrap.auth.authtypes;

import ar.com.acme.bootstrap.auth.support.AuthenticationToken;
import jakarta.servlet.http.HttpServletRequest;

public interface IAuthenticationType {
    AuthenticationToken generateAuthentication(HttpServletRequest request, String authcad);
}
