package ar.com.acme.application.user;

import org.springframework.stereotype.Service;

import ar.com.acme.application.email.IEmailServiceValidator;
import ar.com.acme.application.password.IPasswordService;
import ar.com.acme.application.phone.IPhoneService;
import ar.com.acme.commons.Constants;
import ar.com.acme.model.phone.Phone;
import ar.com.acme.model.user.IUserRepo;
import ar.com.acme.model.user.User;
import ar.com.acme.model.user.errors.UserException;
import jakarta.validation.ConstraintViolationException;
import java.util.UUID;
import java.util.Optional;

@Service
public class UserService extends ar.com.acme.application.templates.service.Service<User, UUID> implements IUserService {
    private final IEmailServiceValidator emailService;
    private final IPasswordService passwordService;

    public UserService(IUserRepo userRepo, IPhoneService phoneService, IPasswordService passwordService, IEmailServiceValidator emailService) {
        super(userRepo);
        this.passwordService = passwordService;
        this.emailService = emailService;
    }

    @Override
    public void validate(User entity) throws ConstraintViolationException {
        // no está definido que no permita repetir solo enter usuarios 'vivos'
        var user = findByEmail(entity.getEmail());
        if (user.isPresent() && user.get().getId() != entity.getId()) {
            throw new UserException(
                Constants.MSJ_REP_ERR_NOVALIDATE
                         .concat(Constants.SYS_CAD_LOGSEP)
                         .concat(User.ERR_REPET_EMAIL),
                entity.getEmail().toLowerCase());
        }

        super.validate(entity);
    }

    @Override
    public Optional<User> findByName(String name) {
        return ((IUserRepo)getRepo()).findByName(name);
    }

    @Override
    public Optional<User> findByToken(UUID token) {
        return ((IUserRepo)getRepo()).findByToken(token);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return ((IUserRepo)getRepo()).findByEmail(email);
    }

    @Override
    public Boolean isValidEmail(String email) {
        return emailService.isValidEmail(email);
    }

    @Override
    public Boolean isValidPassword(String rawPassword) {
        return passwordService.isValidPassword(rawPassword);
    }

    ///////////////////////////////////////////////////////
    // PARA GENERAR LAS CLAVES INICIALES PARA PROBAR:    //
    // (hay que debuggear y parar en la captura de pass  //
    //  y guardar el pass generado en la base de datos)  //
    ///////////////////////////////////////////////////////
    // var passService = new PaswordService();           //
    // var pass = passService.encode("nuevo_password");  //
    ///////////////////////////////////////////////////////
    @Override
    public String encodePassword(CharSequence rawPassword) {
        return passwordService.encode(rawPassword);
    }

    @Override
    public Phone addPhone(User user, Phone phone) {
        if (user == null || phone == null) {
            throw new UserException(
                Constants.MSJ_REP_ERR_NULLITEM,
                Thread.currentThread().getStackTrace()[2].getMethodName());
        }

        phone.setUser(user);
        phone.revive();
        user.getPhones().add(phone);

        return phone;
    }
}
