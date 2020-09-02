package internet.shop.service;

public interface UserService {
    void userSignUp(String command);

    void userLogin(String login, String password);
}
