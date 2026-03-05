package service;

import dao.UserDao;
import dao.impl.JdbcUserDao;
import model.User;

import java.sql.SQLException;


public class SecurityService {



    private final UserDao userDao;

    public SecurityService(UserDao userDao) {
        this.userDao = userDao;
    }


    public User login(String login, String password) throws SQLException {
        if (login == null || password == null) {
            return null;
        }
        User user = userDao.getUserByLogin(login);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public boolean changePassword(String login, String oldPassword, String newPassword) throws SQLException {
        if (login == null || oldPassword == null || newPassword == null) {
            return false;
        }
        User user = userDao.getUserByLogin(login);

        if (user == null) {
            return false;
        }

        if (user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            return  userDao.updateUser(login, user);
        }

        return false;
    }
}
