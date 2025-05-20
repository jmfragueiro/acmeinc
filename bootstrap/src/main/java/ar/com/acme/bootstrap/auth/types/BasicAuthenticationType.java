package ar.com.acme.bootstrap.auth.types;

import java.util.Base64;
import org.springframework.stereotype.Service;

import ar.com.acme.adapter.principal.IPrincipal;
import ar.com.acme.adapter.principal.IPrincipalService;
import ar.com.acme.bootstrap.auth.support.AuthenticationModel;
import ar.com.acme.bootstrap.auth.support.AuthenticationToken;
import ar.com.acme.bootstrap.errors.AuthException;
import ar.com.acme.commons.Constants;
import ar.com.acme.commons.Properties;
import ar.com.acme.bootstrap.jws.JWSException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service(Constants.SYS_CAD_HTTPAUTH_BASIC)
@RequiredArgsConstructor
public class BasicAuthenticationType implements IAuthenticationType {
        private final IPrincipalService<IPrincipal> principalService;
        private final Properties properties;

        @Override
        public AuthenticationToken generateAuthentication(HttpServletRequest request, String authcad) {
            var clientid = properties.getSecurity().get("client_id");
            var clientsecret = properties.getSecurity().get("client_secret");

            ////////////////////////////////////////////////////////////////////////
            // PARA VER/GENERAR CUAL ES EL CODIGO DE CLIENTE QUE DEBE USARSE HAY  //
            // QUE DESCOMENTAR Y DEBUGEAR (VIA BREAKPOINT) EL CODIGO:             //
            // var coded = new String(                                            //
            //                    Base64.getEncoder()                             //
            //                          .encode(authHelper                        //
            //                             .getClientid().concat(":")             //
            //                             .concat(authHelper.getClientsecret())  //
            //                             .getBytes()));                         //
            ////////////////////////////////////////////////////////////////////////
            String authid = new String(Base64.getDecoder().decode(authcad)).split(":")[0];
            String authsecret = new String(Base64.getDecoder().decode(authcad)).split(":")[1];
            if (!(authid.equals(clientid) && authsecret.equals(clientsecret))) {
                throw new JWSException(Constants.MSJ_REQ_ERR_BADREQUESTVALUE);
            }

            var authModel = AuthenticationModel.fromRequest(request);
            if (authModel.username() == null || authModel.password() == null) {
                throw new AuthException(Constants.MSJ_SES_ERR_BADCREDENTIAL);
            }

            var repoUser = principalService.findByName(authModel.username())
                                           .orElseThrow(() -> new AuthException(Constants.MSJ_SES_ERR_BADCREDENTIAL));

            var authorities = repoUser.getAuthorities();

            return new AuthenticationToken(repoUser, authModel.password(), authorities);
        }
}
