package service;

import dao.UserDao;
import dao.impl.InMemoryUserDao;
import model.User;


public class SecurityService {



    private final UserDao userDao;
    private static final SecurityService INSTANCE = new SecurityService();

    private SecurityService() {
        this.userDao = InMemoryUserDao.getInstance();
    }
    public static SecurityService getInstance() {
        return INSTANCE;
    }


    public User login(String login, String password) {
        if (login == null || password == null) {
            return null;
        }
        User user = userDao.getUserByLogin(login);
        if (user == null|| user.getPassword().equals(password)) {
            return user;
        }
        return null;
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
