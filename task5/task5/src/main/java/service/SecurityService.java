package service;

import model.User;


public class SecurityService {

    private final UserService userService = new UserService();

    public boolean login(User user) {
        if (user == null || user.getLogin() == null || user.getPassword() == null) {
            return false;
        }
        User storedUser = userService.getUserByLogin(user.getLogin());
        if (storedUser == null) {
            return false;
        }
        return storedUser.getPassword().equals(user.getPassword());
    }

    public boolean changePassword(String login, String oldPassword, String newPassword) {
        if (login == null || oldPassword == null || newPassword == null) {
            return false;
        }
        User user = userService.getUserByLogin(login);

        if (user == null) {
            return false;
        }

        if (user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            userService.updateUser(login, user);
            return true;
        }

        return false;
    }
}
