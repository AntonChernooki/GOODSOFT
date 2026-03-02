package service;

import dao.UserDao;
import model.User;


public class SecurityService {

    private final UserDao userDao;

    public SecurityService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User login(String login, String password) {
        if (login == null || password == null) {
            return null;
        }
        User user = userDao.getUserByLogin(login);
        if (user == null|| user.getPassword().equals(password)) {
            return null;
        }
        return user;
    }

    public boolean changePassword(String login, String oldPassword, String newPassword) {
        if (login == null || oldPassword == null || newPassword == null) {
            return false;
        }
        User user = userDao.getUserByLogin(login);

        if (user == null) {
            return false;
        }

        if (user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            userDao.updateUser(login, user);
            return true;
        }

        return false;
    }
}
