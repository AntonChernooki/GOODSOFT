package service;

import dao.impl.UserDao;
import model.Role;
import model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    private  final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }


    public Collection<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public User getUserByLogin(String login) {
        return userDao.getUserByLogin(login);
    }

    public boolean addUser(User user) {
        return userDao.addUser(user);
    }

    public boolean deleteUser(String login) {
        return userDao.deleteUser(login);
    }


    public boolean updateUser(String oldLogin, User newUser) {
        return userDao.updateUser(oldLogin,newUser);

    }


}
