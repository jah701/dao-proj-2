package internet.shop.service.impl;

import internet.shop.dao.UserDao;
import internet.shop.lib.Inject;
import internet.shop.lib.Service;
import internet.shop.model.User;
import internet.shop.service.UserService;
import internet.shop.util.HashUtil;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public User create(User user) {
        byte[] userSalt = user.getSalt();
        String hashedPass = HashUtil.hashPassword(user.getPassword(), userSalt);
        user.setPassword(hashedPass);
        user.setSalt(userSalt);
        return userDao.create(user);
    }

    @Override
    public User get(Long id) {
        userDao.get(id);
        return userDao.get(id).get();
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User update(User user) {
        userDao.update(user);
        return user;
    }

    @Override
    public boolean delete(Long id) {
        return userDao.delete(id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userDao.findByLogin(login);
    }
}
