package service;

import model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {
    private static final Map<String, User> users = new HashMap<>();

    static {
        User admin = new User("admin", "1234", "admin@email.com",
                "Админов", "Админ", "Админович", "11-11-2011", "ADMIN");
        User user = new User("user", "4321", "user@email.com",
                "Иванов", "Иван", "Иванович", "12-12-2012", "USER");
        users.put(admin.getLogin(), admin);
        users.put(user.getLogin(), user);
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }

    public User getUserByLogin(String login) {
        return users.get(login);
    }

    public boolean addUser(User user) {
        if (user == null || users.containsKey(user.getLogin())) {
            return false;
        }
        users.put(user.getLogin(), user);
        return true;
    }

    public boolean deleteUser(String login) {
        if (login == null || !users.containsKey(login)) {
            return false;
        }
        users.remove(login);
        return true;
    }


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
