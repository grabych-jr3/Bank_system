package bank.management.system.services;

import bank.management.system.model.User;
import exceptions.AuthenticationException;
import exceptions.LoginException;
import jdbc.dao.UsersDAO;
import jdbc.dao.UsersDAOImpl;

public class UserService {

    private final UsersDAO usersDAO = new UsersDAOImpl();

    public User login(String login, String password){
        User user = usersDAO.findUser(login, password);

        if(user == null){
            throw new AuthenticationException("User not found or incorrect password!");
        }
        return user;
    }

    public User register(String login, String password, String fullName){
        usersDAO.checkLogin(login);
        User user = new User(login, password, fullName);
        usersDAO.createUser(user);
        return user;
    }
}
