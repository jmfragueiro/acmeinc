package ar.com.acme.bootstrap.config;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ar.com.acme.bootstrap.auth.AuthenticationFilter;
import ar.com.acme.bootstrap.auth.support.IAuthenticationPaths;
import ar.com.acme.bootstrap.auth.support.IAuthenticationService;
import ar.com.acme.bootstrap.errors.AccessErrorHandler;
import ar.com.acme.bootstrap.errors.AuthErrorHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecureServerConfig {
    private final AuthErrorHandler authenticationErrorHandler;
    private final AccessErrorHandler accessErrorHandler;
    private final IAuthenticationService authenticationService;
    private final IAuthenticationPaths authenticationPaths;
    private final CorsConfig corsConfig;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            // deshabilita el control de CSRF (ver en producción)
            .csrf(CsrfConfigurer::disable)
            // que no cachee el contexto de seguridad entre llamadas
            .securityContext(AbstractHttpConfigurer::disable)
            // deshabilita el request cache
            .requestCache(RequestCacheConfigurer::disable)
            // establece configurador de CORS
            .cors(csrf -> csrf.configurationSource(corsConfig.corsConfigurationSource()))
            // no se permiten requests anonimos
            .anonymous(AnonymousConfigurer::disable)
            // no utiliza, por ello no genera, el default login form
            .formLogin(FormLoginConfigurer::disable)
            // no utiliza, por ello no genera, default logout
            .logout(LogoutConfigurer::disable)
            // reemplaza el fitro de autenticación por el nuestro
            .addFilterAt(new AuthenticationFilter(authenticationService, authenticationPaths), UsernamePasswordAuthenticationFilter.class)
            // establece el gestor de error de autenticación
            .exceptionHandling(exhand -> exhand.authenticationEntryPoint(authenticationErrorHandler))
            // establece el gestor de error de autorización de acceso
            .exceptionHandling(exhand -> exhand.accessDeniedHandler(accessErrorHandler))
            // deshabilita la gestion de sesiones
            .sessionManagement(AbstractHttpConfigurer::disable)
            // establece los paths publicos y el resto debe ser autenticado
            .authorizeHttpRequests((authorize) -> authorize.requestMatchers(authenticationPaths.getPublicPaths())
                                                           .permitAll()
                                                           .anyRequest()
                                                           .authenticated())
            .build();
    }
}
