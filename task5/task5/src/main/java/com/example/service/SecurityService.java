package com.example.service;

import com.example.dao.UserDao;
import com.example.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
@Transactional
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

    public boolean changePassword(String login, String oldPassword, String newPassword) {
        if (login == null || oldPassword == null || newPassword == null) {
            return false;
        }
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
