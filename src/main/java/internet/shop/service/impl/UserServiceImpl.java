package internet.shop.service.impl;

import internet.shop.dao.UserDao;
import internet.shop.dao.impl.UserDaoImpl;
import internet.shop.lib.Service;
import internet.shop.model.User;
import internet.shop.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public void userSignUp(String signUpCommand) {
        String[] splitRegisterCommand = signUpCommand.split(" ");
        String name = splitRegisterCommand[0];
        String login = splitRegisterCommand[1];

        String password = splitRegisterCommand[2];
        User user = new User(name, login, password);
        UserDao userDao = new UserDaoImpl();
        userDao.addUser(user);
        System.out.println("Nice to meet you, " + user.getName() + "!"
                + "\nYour registration is now completed!"
                + "\nYour login is: " + user.getLogin()
                + "\nYour password is: " + user.getPassword());
    }

    @Override
    public void userLogin(String login, String password) {
    }
}
