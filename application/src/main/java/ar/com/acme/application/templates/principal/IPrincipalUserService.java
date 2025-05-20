package ar.com.acme.application.templates.principal;

import java.util.Optional;

import ar.com.acme.model.base.principal.IPrincipalUser;

/**
 * <p>Esta interfase define el comportamiento deseado de un servicio que permita obtener y,
 * posteriormente, actualizar a los objetos {@link IPrincipalUser} con información nueva
 * proveniente del requerimiento web actual.</p>
 * <p> Se espera que la implementación concreta de esta interfase se realice en el módulo
 * {@code application} de manera de desacoplar el módulo {@code bootstrap} de los módulos
 * que representan a la aplicación específica (como {@code model} y {@code adapter}).</p>
 *
 * @author jmfragueiro
 * @version 20250520
 * @see IPrincipalService
 * @see IPrincipal
 * @see IPrincipalUser
 */
public interface IPrincipalUserService<P extends IPrincipalUser<TKI>, TKI> {
    Optional<P> findByName(String name);

    Optional<P> findByToken(TKI token);

    // se usa Object por la invariancia
    // de los tipos genéricos de java
    P update(Object user);
}
