package com.example.service;

import com.example.dao.UserDao;
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


    public Collection<User> getAllUsers() throws SQLException {
        return userDao.getAllUsers();
    }

    public User getUserByLogin(String login) throws SQLException {
        return userDao.getUserByLogin(login);
    }

    public boolean addUser(String login, String password, String email,
                           String surname, String name, String patronymic,
                           LocalDate birthday, Set<Role> roles, Map<String, String> errors) {
        try {
            if (userDao.getUserByLogin(login) != null) {
                errors.put("login", "Пользователь с таким логином уже существует");
                return false;
            }
            User user = new User(login, password, email, surname, name, patronymic, birthday, roles);
            userDao.addUser(user);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(String originalLogin, String login, String password, String email,
                              String surname, String name, String patronymic,
                              LocalDate birthday, Set<Role> roles, Map<String, String> errors) {
        try {
            if (!originalLogin.equals(login) && userDao.getUserByLogin(login) != null) {
                errors.put("login", "Пользователь с таким логином уже существует");
                return false;
            }

            User user = new User(login, password, email, surname, name, patronymic, birthday, roles);
            userDao.updateUser(originalLogin, user);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(String login) {
        try {
            userDao.deleteUser(login);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }





}
