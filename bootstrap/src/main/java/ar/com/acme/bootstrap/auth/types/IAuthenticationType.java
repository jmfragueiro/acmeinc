package ar.com.acme.bootstrap.auth.types;

import ar.com.acme.bootstrap.auth.support.AuthenticationToken;
import jakarta.servlet.http.HttpServletRequest;

public interface IAuthenticationType {
    AuthenticationToken generateAuthentication(HttpServletRequest request, String authcad);
}
