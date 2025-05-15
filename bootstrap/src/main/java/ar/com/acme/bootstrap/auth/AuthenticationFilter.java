package ar.com.acme.bootstrap.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ar.com.acme.bootstrap.auth.support.AutenticationContext;
import ar.com.acme.bootstrap.auth.support.IAuthenticationPaths;
import ar.com.acme.bootstrap.auth.support.IAuthenticationService;
import ar.com.acme.bootstrap.errors.AuthException;

import java.io.IOException;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final IAuthenticationPaths authenticationPaths;

    public AuthenticationFilter(IAuthenticationService authenticationService, IAuthenticationPaths authenticationPaths) {
        super(authenticationService);
        this.authenticationPaths = authenticationPaths;
        super.setPostOnly(false);
     }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return !authenticationPaths.getPublicPaths().matches(request);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            return ((IAuthenticationService)getAuthenticationManager()).authenticateFromRequest(request);
        } catch (Exception ex) {
            throw new AuthException(ex.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain,
        Authentication authenticationResult) throws IOException, ServletException {
        AutenticationContext.setContextAuthentication(authenticationResult);

        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException failed) throws IOException, ServletException {
        AutenticationContext.clearContextAuthentication();

        response.sendError(HttpStatus.UNAUTHORIZED.value(), failed.getMessage());
    }
}
