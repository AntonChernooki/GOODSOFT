package com.example.service;

import com.example.dao.UserDao;
import com.example.model.Role;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;


@Service
public class UserService {
    @Autowired
    private  UserDao userDao;


    public Collection<User> getAllUsers() throws SQLException {
        return userDao.getAllUsers();
    }

    public User getUserByLogin(String login) throws SQLException {
        return userDao.getUserByLogin(login);
    }

    public boolean addUser(String login, String password, String email,
                           String surname, String name, String patronymic,
                           String birthday, Set<Role> roles, Map<String, String> errors) {
        try {
            Map<String, String> validationErrors = validateUserData(login, password, email,
                    surname, name, patronymic, birthday, roles, null);
            if (!validationErrors.isEmpty()) {
                errors.putAll(validationErrors);
                return false;
            }

            User user = new User(login, password, email, surname, name, patronymic, birthday, roles);
            userDao.addUser(user);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            errors.put("global", "Ошибка бд: не удалось добавить пользователя");
            return false;
        }
    }

    public boolean updateUser(String originalLogin, String login, String password, String email,
                              String surname, String name, String patronymic,
                              String birthday, Set<Role> roles, Map<String, String> errors) {
        try {
            Map<String, String> validationErrors = validateUserData(login, password, email,
                    surname, name, patronymic, birthday, roles, originalLogin);
            if (!validationErrors.isEmpty()) {
                errors.putAll(validationErrors);
                return false;
            }

            User user = new User(login, password, email, surname, name, patronymic, birthday, roles);
            userDao.updateUser(originalLogin, user);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            errors.put("global", "Ошибка бд: не удалось обновить пользователя");
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


    private Map<String, String> validateUserData(String login, String password, String email, String surname, String name, String patronymic,
                                                 String birthday, Set<Role>roles, String originalLogin) throws SQLException {
        Map<String, String> errors = new HashMap<>();

        if (login == null || login.trim().isEmpty()) {
            errors.put("login", "Логин обязателен");
        }
        if (password == null || password.trim().isEmpty()) {
            errors.put("password", "Пароль обязателен");
        }
        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "Имя обязательно");
        }
        if (email == null || email.trim().isEmpty()) {
            errors.put("email", "Email обязателен");
        }
        if (surname == null || surname.trim().isEmpty()) {
            errors.put("surname", "Фамилия обязательна");
        }
        if (birthday == null || birthday.trim().isEmpty()) {
            errors.put("birthday", "Дата рождения обязательна");
        }
        if (roles == null || roles.isEmpty()) {
            errors.put("role", "Роль обязательна");
        } /*else {
            try {
                Role role = Role.valueOf(roleParam.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                errors.put("role", "Недопустимое значение роли");
            }
        }*/
        if (login != null && !login.trim().isEmpty()) {
            User existing = userDao.getUserByLogin(login);
            if (existing != null && !Objects.equals(login, originalLogin)) {
                errors.put("login", "Пользователь с таким логином уже существует");
            }
        }

        return errors;
    }


}
