package dao;

import model.User;

import java.sql.SQLException;
import java.util.Collection;

public interface UserDao {
    Collection<User> getAllUsers() throws SQLException;

    User getUserByLogin(String login) throws SQLException;

    void addUser(User user) throws SQLException;

    void updateUser(String oldLogin, User newUser) throws SQLException;

    void deleteUser(String login) throws SQLException;
}
