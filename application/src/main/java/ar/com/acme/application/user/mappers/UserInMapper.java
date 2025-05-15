package ar.com.acme.application.user.mappers;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import ar.com.acme.application.phone.IPhoneService;
import ar.com.acme.application.phone.mappers.PhoneInMapper;
import ar.com.acme.application.user.IUserService;
import ar.com.acme.model.user.User;
import ar.com.acme.model.user.errors.UserException;
import ar.com.acme.commons.Constants;

public record UserInMapper(UUID id,
                             String name,
                             String email,
                             String password, // null si no hay que modificar
                             Boolean active,
                             Collection<PhoneInMapper> phones) {
    public User toUser(IUserService userService, IPhoneService phoneService) {
        User user;

        // si viene con id entonces busca el usuario para tomar valores
        if (this.id != null) {
            user = userService.findAliveById(this.id())
                              .orElseThrow(() -> new UserException(Constants.MSJ_REP_ERR_NOITEM, User.ENTITY_NAME));
        } else {
            user = new User();
        }

        // si no viene nulo el name, entonces lo carga/modifica
        if (this.name() != null) {
            user.setName(this.name());
        }

        // si no viene en nulo el pass, entonces se valida y carga/modifica
        if (this.password() != null) {
            if (!userService.isValidPassword(this.password())) {
                throw new UserException(User.ERR_BAD_PASSWORD, this.password());
            }
            user.setPassword(userService.encodePassword(this.password()));
        }

        // el email es obligatorio: un email nulo no deberia pasar la validacion
        if (!userService.isValidEmail(this.email())) {
            throw new UserException(User.ERR_BAD_EMAIL, this.email());
        }
        user.setEmail(this.email());

        // al crearse el usuario se activa el mismo, si no, toma lo que viene
        if (user.isNew()) {
            user.setActive(true);
        } else if (this.active() != null) {
            user.setActive(this.active());
        }

        // si trae algo de telefonos, entonces actualiza la lista reavivando
        // los posibles telefonos que hayn sido previamente eliminados
        if (!this.phones().isEmpty()) {
            user.setPhones(this.phones()
                               .stream()
                               .map(p -> p.toPhone(phoneService))
                               .peek(phone -> userService.addPhone(user, phone))
                               .collect(Collectors.toSet()));
        }

        return user;
    }
 }
