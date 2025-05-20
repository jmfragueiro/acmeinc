package ar.com.acme.commons.principal;

import java.time.LocalDateTime;

/**
 * <p>Esta interfase define el comportamiento deseado de un objeto que representa al "usuario"
 * contenido dentro de un objeto {@link IPrincipal}. Se espera que el objeto usuario pueda ser
 * identificado mediante un token específico único para el mismo como lo pide {@link IPrincipal}.</p>
 * <p> Se espera que la implementación concreta de esta interfase se realice en el módulo
 * {@code model} de manera de desacoplar el módulo {@code bootstrap} de los módulos que
 * representan a la aplicación específica (como {@code model} y {@code adapter}).</p>
 *
 * @param <U>   El tipo concreto del objeto usuario dentro del sistema
 * @param <TKI> El tipo del token de identificacion para un objeto usuario
 * @author jmfragueiro
 * @version 20250520
 * @see IPrincipalUserService
 * @see IPrincipalService
 * @see IPrincipal
 */
public interface IPrincipalUser<TKI> {
    String getName();

    String getPassword();

    void setToken(TKI token);

    TKI getToken();

    void setLastLogin(LocalDateTime lastLogin);

    LocalDateTime getLastLogin();

    boolean canOperate();
}
