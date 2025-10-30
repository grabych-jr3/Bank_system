package jdbc.dao;

import bank.management.system.model.User;

public interface UsersDAO {
    User createUser(User user);
    User findUser(String login, String password);
    void checkLogin(String login);
}
