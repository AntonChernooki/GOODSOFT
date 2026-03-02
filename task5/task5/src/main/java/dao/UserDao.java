package dao;

import model.User;

import java.util.Collection;

public interface UserDao {
    Collection<User> getAllUsers();
    User getUserByLogin(String login);
    boolean addUser(User user);
    boolean updateUser(String login, User user);
    boolean deleteUser(String login);
}
