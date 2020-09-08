package internet.shop.dao.impl;

import internet.shop.dao.UserDao;
import internet.shop.db.Storage;
import internet.shop.lib.Dao;
import internet.shop.model.User;
import java.util.List;
import java.util.Optional;

@Dao
public class UserDaoImpl implements UserDao {
    @Override
    public User create(User user) {
        Storage.addUser(user);
        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        return Storage.users.stream()
                .filter(o -> o.getId().equals(id)).findFirst();
    }

    @Override
    public List<User> getAll() {
        return Storage.users;
    }

    @Override
    public User update(User user) {
        for (int i = 0; i < Storage.users.size(); i++) {
            if (Storage.users.get(i).getId().equals(user.getId())) {
                Storage.users.set(i, user);
            }
        }
        return user;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.users.remove(get(id).orElseThrow());
    }
}
