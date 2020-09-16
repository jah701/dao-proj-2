package internet.shop.dao;

import internet.shop.model.User;
import java.util.Optional;

public interface UserDao extends GenericDao<User, Long> {
    Optional<User> findByLogin(String login);
}
