package ar.com.acme.bootstrap.auth.support;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Esta interface establece el comportamiento necesario para "engancharse" al mecanismo
 * de Autenticación de springboot, es decir para dar soporte efectivo a las operaciones
 * de autenticación del framework, pero desde un esquema mínimo bien específico.
 *
 * @author jmfragueiro
 * @version 20250505
 */
public interface IAuthenticationService extends AuthenticationManager {
    String authenticateAndLogin(HttpServletRequest request) throws AuthenticationException;

    String authenticateAndLogout(HttpServletRequest request) throws AuthenticationException;

    Authentication authenticateFromRequest(HttpServletRequest request) throws AuthenticationException;
}
