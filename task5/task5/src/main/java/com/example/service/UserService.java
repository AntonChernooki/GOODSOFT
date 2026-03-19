package com.example.service;

import com.example.dao.UserDao;
import com.example.exeption.DatabaseException;
import com.example.exeption.UserAlreadyExistsException;
import com.example.exeption.UserNotFoundException;
import com.example.model.Role;
import com.example.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;


@Service
@Transactional
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }


    public Collection<User> getAllUsers()  {
        try {
            return userDao.getAllUsers();
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка базы данных при получении списка пользователей", e);
        }
    }

    public User getUserByLogin(String login)  {
        try {
            User user = userDao.getUserByLogin(login);
            if (user == null) {
                throw new UserNotFoundException(login);
            }
            return user;
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка базы данных при получении пользователя по логину", e);
        }
    }

    public void addUser(String login, String password, String email,
                        String surname, String name, String patronymic,
                        LocalDate birthday, Set<Role> roles) {
        try {
            if (userDao.getUserByLogin(login) != null) {
                throw new UserAlreadyExistsException(login);
            }
            User user = new User(login, password, email, surname, name, patronymic, birthday, roles);
            userDao.addUser(user);

        } catch (SQLException e) {
            throw new DatabaseException("Ошибка базы данных при добавлении пользователя", e);
        }
    }

    public void updateUser(String originalLogin, String login, String password, String email,
                           String surname, String name, String patronymic,
                           LocalDate birthday, Set<Role> roles) {
        try {
            if (!originalLogin.equals(login) && userDao.getUserByLogin(login) != null) {
                throw new UserAlreadyExistsException(login);
            }

            User user = new User(login, password, email, surname, name, patronymic, birthday, roles);
            userDao.updateUser(originalLogin, user);

        } catch (SQLException e) {
            throw new DatabaseException("Ошибка базы данных при обновлении пользователя", e);
        }
    }

    public void deleteUser(String login) {
        try {
            User user = userDao.getUserByLogin(login);
            if (user == null) {
                throw new UserNotFoundException(login);
            }
            userDao.deleteUser(login);
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка базы данных при удалении пользователя", e);
        }
    }


}
