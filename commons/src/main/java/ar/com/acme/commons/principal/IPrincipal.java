package ar.com.acme.commons.principal;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * <p>Esta interfase define el comportamiento deseado de un objeto que representa al "usuario
 * actualmente conectado" y de cara al al contexto de seguridad que se aplique dentro del
 * sistema, para que pueda ser recuperado para validaciones, controles de acceso, etc. Se
 * espera que sea un objeto que solo tenga vida durante el procesamiento del requerimiento
 * web actual. Actúa además, junto con su servicio, como unico punto de relación entre la
 * módulo de aplicación y el de bootstrap, permitiendo evitar el acoplamiento etre estos
 * módulos.</p>
 * <p>Se espera que un objeto Principal se asocie internamente a la entidad, dentro de la
 * aplicación específica, que representa a un usuario concreto dentro del sistema, la que
 * entonces será desconocida para el mecanismo de contexto de seguridad aplicado por el
 * framework utilizado (representado aqui por el módulo {@code bootstrap}). Se espera
 * que el objeto usuario pueda ser identificado mediante un token específico único para
 * el mismo.</p>
 * <p> Se espera que la implementación concreta de esta interfase se realice en el módulo
 * {@code adapter} de manera de desacoplar el módulo {@code bootstrap} de los módulos que
 * representan a la aplicación específica (como {@code model} y {@code adapter}).</p>
 *
 * @param <U>   El tipo concreto del objeto usuario dentro del sistema
 * @param <TKI> El tipo del token de identificacion para un objeto usuario
 * @author jmfragueiro
 * @version 20250520
 * @see IPrincipalService
 * @see IPrincipalUserService
 * @see IPrincipalUser
 */
public interface IPrincipal<TKI> {
    IPrincipalUser<TKI> getUser();

    String getName();

    void setToken(TKI token);

    TKI getToken();

    void setLastLogin(LocalDateTime lastLogin);

    LocalDateTime getLastLogin();

    boolean canOperate();

    boolean matchCredential(String rawCredential);

    Collection<String> getAuthorities();
}
