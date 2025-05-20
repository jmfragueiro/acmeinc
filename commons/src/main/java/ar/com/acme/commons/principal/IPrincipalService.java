package ar.com.acme.commons.principal;

import java.util.Optional;

/**
 * <p>Esta interfase define el comportamiento deseado de un servicio que permita obtener y,
 * posteriormente, actualizar a los objetos gestionados dentro de {@link IPrincipal} con la
 * información asociada a su utilización durante el procesamiento del requerimiento web actual.</p>
 * <p>Esta interfase representa, junto con {@link IPrincipal}, lo únicos puntos de relación
 * entre los módulos application y bootstrap.</p>
 * <p> Se espera que la implementación concreta de esta interfase se realice en el módulo
 * {@code adapter} de manera de desacoplar el módulo {@code bootstrap} de los módulos que
 * representan a la aplicación específica (como {@code model} y {@code adapter}).</p>
 *
 * @author jmfragueiro
 * @version 20250520
 * @see IPrincipal
 * @see IPrincipalUserService
 * @see IPrincipalUser
 */
public interface IPrincipalService<P extends IPrincipal<TKI>, TKI> {
    Optional<P> findByName(String name);

    Optional<P> findByToken(TKI token);

    void updatePrincipal(P principal);
}
