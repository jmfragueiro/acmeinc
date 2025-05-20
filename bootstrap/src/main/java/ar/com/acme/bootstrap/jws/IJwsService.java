package ar.com.acme.bootstrap.jws;

import java.time.LocalDateTime;
import java.util.Collection;

import ar.com.acme.adapter.principal.Principal;

/**
 * Esta interface implementa el comportamiento necesario aquí para el servicio
 * de generación y operación de elementos JWS (JSON Web Signature), los cuales
 * se utilizan aquí para el intercambio de información de sesiones activas, con
 * posibilidad de agregar datos "extras" a las mismas (propios de la aplicación
 * que utilice este "proto-framework").
 *
 * @author jmfragueiro
 * @version 20250505
 */
public interface IJwsService {
    String generateJws(Principal source);

    void validateJws(String jws);

    String getIdFromJws(String jws);

    String getNameFromJws(String jws);

    LocalDateTime getIatFromJws(String jws);

    Collection<String> getAuthoritiesFromJws(String jws);
}
