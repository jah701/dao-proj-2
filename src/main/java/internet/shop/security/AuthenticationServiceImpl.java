package internet.shop.security;

import internet.shop.exceptions.AuthenticationException;
import internet.shop.lib.Inject;
import internet.shop.lib.Service;
import internet.shop.model.User;
import internet.shop.service.UserService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final String ERR_MSG = "Incorrect username or password";

    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        User userFromDb = userService.findByLogin(login).orElseThrow(() ->
                new AuthenticationException(ERR_MSG));
        if (userFromDb.getPassword().equals(password)) {
            return userFromDb;
        }
        throw new AuthenticationException(ERR_MSG);
    }
}
