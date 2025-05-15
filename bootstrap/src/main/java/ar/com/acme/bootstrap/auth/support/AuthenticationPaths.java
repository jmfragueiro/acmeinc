package ar.com.acme.bootstrap.auth.support;

import java.util.Arrays;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import ar.com.acme.commons.Properties;

@Service
public class AuthenticationPaths implements IAuthenticationPaths {
    private final RequestMatcher publicPaths;

    public AuthenticationPaths(Properties properties) {
        this.publicPaths = new OrRequestMatcher(
                                    Arrays.stream(properties.getSecurity().get("public_paths").split(","))
                                          .map(AntPathRequestMatcher::new)
                                          .toArray(RequestMatcher[]::new));
    }

    @Override
    public RequestMatcher getPublicPaths() {
        return publicPaths;
    }
}
