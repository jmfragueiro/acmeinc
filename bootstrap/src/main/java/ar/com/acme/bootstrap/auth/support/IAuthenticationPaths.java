package ar.com.acme.bootstrap.auth.support;

import org.springframework.security.web.util.matcher.RequestMatcher;

public interface IAuthenticationPaths {
    public RequestMatcher getPublicPaths();
}
