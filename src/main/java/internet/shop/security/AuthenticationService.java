package internet.shop.security;

import internet.shop.exceptions.AuthenticationException;
import internet.shop.model.User;

public interface AuthenticationService {
    User login(String login, String password) throws AuthenticationException;
}
