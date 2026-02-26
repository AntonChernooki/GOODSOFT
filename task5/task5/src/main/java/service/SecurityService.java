package service;

import model.User;

import java.util.HashMap;
import java.util.Map;

public class SecurityService {

    private static final Map<String, String> users = new HashMap<>();

    static {
        users.put("admin", "1234");
        users.put("user", "4321");
    }

    public boolean login(User user) {
        if (user == null || user.getUsername() == null || user.password == null) {
            return false;
        }
        String storedPassw = users.get(user.getUsername());
        if (storedPassw==null){
            return false;
        }
        return storedPassw.equals(user.getPassword());
    }

    public boolean changePassword(String username, String oldPassword, String newPassword) {
        if(username==null|| oldPassword==null||newPassword==null){
            return false;
        }
        String storedPassw = users.get(username);
        if (storedPassw==null){
            return false;
        }
        if (storedPassw.equals(oldPassword)) {
            users.put(username, newPassword);
            return true;
        }
        return false;
    }
}
