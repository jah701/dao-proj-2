package internet.shop.security;

import internet.shop.exceptions.AuthenticationException;
import internet.shop.lib.Inject;
import internet.shop.lib.Service;
import internet.shop.model.User;
import internet.shop.service.UserService;
import internet.shop.util.HashUtil;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> userFromDb = userService.findByLogin(login);
        if (userFromDb.isEmpty()) {
            throw new AuthenticationException("Incorrect data entered");
        }
        byte[] salt = userFromDb.get().getSalt();
        String saltedPass = HashUtil.hashPassword(password, salt);
        if (userFromDb.get().getPassword().equals(saltedPass)) {
            return userFromDb.get();
        }
        throw new AuthenticationException("Something went wrong while logging in");
    }
}
