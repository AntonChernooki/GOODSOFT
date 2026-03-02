package dao.impl;

import dao.UserDao;
import model.Role;
import model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InMemoryUserDao implements UserDao {
    private static final Map<String, User> users = new HashMap<>();

    static {
        User admin = new User("admin", "1234", "admin@email.com",
                "Админов", "Админ", "Админович", "11-11-2011", Role.ADMIN);
        User user = new User("user", "4321", "user@email.com",
                "Иванов", "Иван", "Иванович", "12-12-2012", Role.USER);
        users.put(admin.getLogin(), admin);
        users.put(user.getLogin(), user);
    }

    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @Override
    public User getUserByLogin(String login) {
        return users.get(login);
    }

    @Override
    public boolean addUser(User user) {
        if (user == null || users.containsKey(user.getLogin())) {
            return false;
        }
        users.put(user.getLogin(), user);
        return true;
    }

    @Override
    public boolean deleteUser(String login) {
        if (login == null || !users.containsKey(login)) {
            return false;
        }
        users.remove(login);
        return true;
    }


    @Override
    public boolean updateUser(String oldLogin, User newUser) {
        if (oldLogin == null || newUser == null || !users.containsKey(oldLogin)) {
            return false;
        }
        if (!oldLogin.equals(newUser.getLogin())) {
            if (users.containsKey(newUser.getLogin())) {
                return false;
            }
            users.remove(oldLogin);
            users.put(newUser.getLogin(), newUser);
        } else {
            users.put(oldLogin, newUser);
        }
        return true;
    }
}
