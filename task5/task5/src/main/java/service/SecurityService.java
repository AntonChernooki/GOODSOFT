package service;

import dao.UserDao;
import model.User;


public class SecurityService {

    private final UserDao userDao ;

    public SecurityService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean login(User user) {
        if (user == null || user.getLogin() == null || user.getPassword() == null) {
            return false;
        }
        User storedUser = userDao.getUserByLogin(user.getLogin());
        if (storedUser == null) {
            return false;
        }
        return storedUser.getPassword().equals(user.getPassword());
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
