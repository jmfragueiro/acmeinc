package ar.com.acme.application.user;

import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import ar.com.acme.model.user.User;
import ar.com.acme.application.phone.IPhoneService;
import ar.com.acme.application.templates.controller.Controller;
import ar.com.acme.application.user.mappers.UserInMapper;
import ar.com.acme.application.user.mappers.UserOutMapper;
import ar.com.acme.commons.Constants;

@RestController
@RequestMapping(Constants.URL_CONTROLLER_USER)
public class UserController extends Controller<User, UUID, UserInMapper, UserOutMapper> {
    private final IPhoneService phoneService;

    public UserController(IUserService service, IPhoneService phoneService) {
        super(service);
        this.phoneService = phoneService;
    }

    @Override
    public UserOutMapper toWebOutModel(User source) {
        return UserOutMapper.fromUser(source);
    }

    @Override
    public User fromWebInModel(UserInMapper source) {
        return source.toUser((IUserService)getService(), phoneService);
    }
}
