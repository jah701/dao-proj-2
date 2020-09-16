package internet.shop.security;

import internet.shop.exceptions.AuthenticationException;
import internet.shop.lib.Inject;
import internet.shop.lib.Service;
import internet.shop.model.User;
import internet.shop.service.UserService;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> userFromDb = userService.findByLogin(login);

        if (userFromDb.isPresent() && userFromDb.get().getPassword().equals(password)) {
            return userFromDb.get();
        }
        throw new AuthenticationException("Incorrect data entered");
    }
}
