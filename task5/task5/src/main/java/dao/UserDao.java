package dao;

import model.User;

import java.sql.SQLException;
import java.util.Collection;

public interface UserDao {
    Collection<User> getAllUsers() throws SQLException;

    User getUserByLogin(String login) throws SQLException;

    boolean addUser(User user);

    boolean updateUser(String login, User user);

    boolean deleteUser(String login);
}
