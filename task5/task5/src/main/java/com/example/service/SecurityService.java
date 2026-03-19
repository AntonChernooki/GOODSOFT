package com.example.service;

import com.example.dao.UserDao;
import com.example.exeption.DatabaseException;
import com.example.exeption.InvalidCredentialsException;
import com.example.exeption.UserNotFoundException;
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


    public User login(String login, String password) {
        try {
            User user = userDao.getUserByLogin(login);
            if (user != null && user.getPassword().equals(password)) {
                return user;
            }
            throw new InvalidCredentialsException();
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка базы данных при попытки входа", e);
        }
    }

    public void changePassword(String login, String oldPassword, String newPassword) {

        try {
            User user = userDao.getUserByLogin(login);
            if (user == null) {
                throw new UserNotFoundException(login);
            }
            if (user.getPassword().equals(oldPassword)) {
                user.setPassword(newPassword);
                userDao.updateUser(login, user);
                return;

            }
            throw new InvalidCredentialsException();
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка базы данных при смене пароля", e);

        }
    }
}
