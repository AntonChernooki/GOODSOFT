package service;

import dao.UserDao;
import dao.impl.InMemoryUserDao;
import model.Role;
import model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserService {
    private final UserDao userDao;



    private static final UserService INSTANCE = new UserService();

    private UserService() {
        this.userDao = InMemoryUserDao.getInstance();
    }
    public static UserService getInstance() {
        return INSTANCE;
    }

    public Collection<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public User getUserByLogin(String login) {
        return userDao.getUserByLogin(login);
    }

    public boolean addUser(String login, String password, String email,
                           String surname, String name, String patronymic,
                           String birthday, String roleParam, Map<String, String> errors) {
        Map<String, String> validationErrors = validateUserData(login, password, email,
                surname, name, patronymic, birthday, roleParam, null);
        if (!validationErrors.isEmpty()) {
            errors.putAll(validationErrors);
            return false;
        }


        Role role = Role.valueOf(roleParam.trim().toUpperCase());
        User user = new User(login, password, email, surname, name, patronymic, birthday, role);


        boolean success = userDao.addUser(user);
        if (!success) {
            errors.put("global", "Не удалось добавить пользователя");
        }
        return success;
    }

    public boolean updateUser(String originalLogin, String login, String password, String email,
                              String surname, String name, String patronymic,
                              String birthday, String roleParam, Map<String, String> errors) {

        Map<String, String> validationErrors = validateUserData(login, password, email, surname, name, patronymic, birthday, roleParam, originalLogin);
        if (!validationErrors.isEmpty()) {
            errors.putAll(validationErrors);
            return false;
        }

        Role role = Role.valueOf(roleParam.trim().toUpperCase());
        User user = new User(login, password, email, surname, name, patronymic, birthday, role);

        boolean success = userDao.updateUser(originalLogin, user);
        if (!success) {
            errors.put("global", "Не удалось обновить пользователя");
        }
        return success;
    }

    public boolean deleteUser(String login) {
        return userDao.deleteUser(login);
    }






    private Map<String, String> validateUserData(String login, String password, String email,  String surname, String name, String patronymic,
                                                 String birthday, String roleParam, String originalLogin) {
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
        if (roleParam == null || roleParam.trim().isEmpty()) {
            errors.put("role", "Роль обязательна");
        } else {
            try {
                Role role = Role.valueOf(roleParam.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                errors.put("role", "Недопустимое значение роли");
            }
        }
        if (login != null && !login.trim().isEmpty()) {
            User existing = userDao.getUserByLogin(login);
            if (existing != null && !Objects.equals(login, originalLogin)) {
                errors.put("login", "Пользователь с таким логином уже существует");
            }
        }

        return errors;
    }


}
