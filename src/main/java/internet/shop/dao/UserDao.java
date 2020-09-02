package internet.shop.dao;

import internet.shop.model.Order;
import internet.shop.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    void addUser(User user);

    Optional<Order> getUser(User user);

    Optional<Order> getUserById(Long id);

    boolean removeUser(User user);

    boolean removeUserById();

    List<Order> getAllUsers();
}
