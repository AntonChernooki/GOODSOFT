package com.example.service;

import com.example.dao.UserDao;
import com.example.exeption.DatabaseException;
import com.example.exeption.InvalidCredentialsException;
import com.example.exeption.UserNotFoundException;
import com.example.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
@Transactional
public class SecurityService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public SecurityService(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }


    public User login(String login, String password) {
        try {
            User user = userDao.getUserByLogin(login);
            if (user != null && passwordEncoder.matches(password, user.getPassword())) {
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
            if (passwordEncoder.matches(oldPassword, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userDao.updateUser(login, user);
                return;

            }
            throw new InvalidCredentialsException();
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка базы данных при смене пароля", e);

        }
    }
}
