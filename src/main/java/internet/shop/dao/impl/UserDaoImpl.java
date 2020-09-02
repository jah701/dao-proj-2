package internet.shop.dao.impl;

import internet.shop.dao.UserDao;
import internet.shop.db.Storage;
import internet.shop.lib.Dao;
import internet.shop.model.Order;
import internet.shop.model.User;
import java.util.List;
import java.util.Optional;

@Dao
public class UserDaoImpl implements UserDao {
    @Override
    public void addUser(User user) {
        Storage.addUser(user);
    }

    @Override
    public Optional<Order> getUser(User user) {
        return Optional.empty();
    }

    @Override
    public Optional<Order> getUserById(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean removeUser(User user) {
        return false;
    }

    @Override
    public boolean removeUserById() {
        return false;
    }

    @Override
    public List<Order> getAllUsers() {
        return null;
    }
}
