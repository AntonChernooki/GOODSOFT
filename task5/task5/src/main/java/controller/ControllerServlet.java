package controller;

import constants.Constants;
import model.User;
import service.SecurityService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class ControllerServlet extends HttpServlet {
    private final SecurityService securityService = new SecurityService();
    private final UserService userService = new UserService();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        switch (servletPath) {
            case "/login.jhtml": {
                handleLogin(req, resp);
                break;
            }
            case "/welcome.jhtml": {
                handleWelcome(req, resp);
                break;
            }
            case "/loginedit.jhtml": {
                handleEdit(req, resp);
                break;
            }
            case "/userlist.jhtml": {
                handleUserList(req, resp);
                break;
            }
            case "/useredit.jhtml": {
                handleUserEdit(req, resp);
                break;
            }
            case "/userdelete.jhtml": {
                handleUserDelete(req, resp);
                break;
            }
            default: {
                resp.sendRedirect(req.getContextPath() + "/welcome.jhtml");
            }
        }


    }

    private void handleUserDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(Constants.USER_SESSION_KEY);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jhtml");
            return;
        }

        if (!"ADMIN".equals(user.getRole())) {
            req.setAttribute("error", "пользователь не админ");
            req.getRequestDispatcher("/WEB-INF/jsp/welcome.jsp").forward(req, resp);
            return;
        }
        String login = req.getParameter("login");
        if (login == null || login.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/userlist.jhtml");
            return;
        }
        if (Objects.equals(login, user.getLogin())) {
            req.setAttribute("error", "Нельзя удалить свою учётную запись");
            handleUserList(req, resp);
            return;
        }

        userService.deleteUser(login);
        resp.sendRedirect(req.getContextPath() + "/userlist.jhtml");

    }

    private void handleUserEdit(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession httpSession = req.getSession();
        User user = (User) httpSession.getAttribute(Constants.USER_SESSION_KEY);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jhtml");
            return;
        }

        if (!"ADMIN".equals(user.getRole())) {
            req.setAttribute("error", "пользователь не админ");
            req.getRequestDispatcher("/WEB-INF/jsp/welcome.jsp").forward(req, resp);
            return;
        }
        String action = req.getParameter(Constants.ACTION_PARAM);
        if (Constants.SAVE_PARAM.equals(action)) {
            handleUserSave(req, resp);
        } else {
            String login = req.getParameter(Constants.LOGIN_PARAM);
            User editUser = null;
            if (login != null && !login.isEmpty()) {
                editUser = userService.getUserByLogin(login);
                if (editUser == null) {
                    req.setAttribute("error", "Пользователь не найден");
                    resp.sendRedirect(req.getContextPath() + "/userlist.jhtml");
                    return;
                }
            }
            req.setAttribute("user", editUser);
            req.getRequestDispatcher("/WEB-INF/jsp/useredit.jsp").forward(req, resp);


        }
    }

    private void handleUserSave(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter(Constants.LOGIN_PARAM);
        String password = req.getParameter(Constants.PASSWORD_PARAM);
        String email = req.getParameter(Constants.EMAIL_PARAM);
        String surname = req.getParameter(Constants.SURNAME_PARAM);
        String name = req.getParameter(Constants.NAME_PARAM);
        String patronymic = req.getParameter(Constants.PATRONYMIC_PARAM);
        String birthday = req.getParameter(Constants.BIRTHDAY_PARAM);
        String role = req.getParameter(Constants.ROLE_PARAM);
        String originalLogin = req.getParameter("originalLogin"); // скрытое поле из формы

        Map<String, String> errors = new HashMap<>();

        if (login == null || login.trim().isEmpty()) errors.put("login", "Логин обязателен");
        if (password == null || password.trim().isEmpty()) errors.put("password", "Пароль обязателен");
        if (name == null || name.trim().isEmpty()) errors.put("name", "Имя обязательно");
        if (email == null || email.trim().isEmpty()) errors.put("email", "Email обязателен");
        if (surname == null || surname.trim().isEmpty()) errors.put("surname", "Фамилия обязательна");
        if (birthday == null || birthday.trim().isEmpty()) errors.put("birthday", "Дата рождения обязательна");
        if (role == null || role.trim().isEmpty()) errors.put("role", "Роль обязательна");

        if (login != null && !login.trim().isEmpty()) {
            User existing = userService.getUserByLogin(login);
            if (existing != null) {
                if (!login.equals(originalLogin)) {
                    errors.put("login", "Пользователь с таким логином уже существует");
                }
            }
        }

        if (!errors.isEmpty()) {
            User tempUser = new User(login, password, email, surname, name, patronymic, birthday, role);
            req.setAttribute("user", tempUser);
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/WEB-INF/jsp/useredit.jsp").forward(req, resp);
            return;
        }

        User newUser = new User(login, password, email, surname, name, patronymic, birthday, role);
        boolean success;

        if (originalLogin == null || originalLogin.isEmpty()) {
            success = userService.addUser(newUser);
        } else {
            success = userService.updateUser(originalLogin, newUser);
        }

        if (success) {
            resp.sendRedirect(req.getContextPath() + "/userlist.jhtml");
        } else {
            req.setAttribute("error", "Не удалось сохранить пользователя");
            req.setAttribute("user", newUser);
            req.getRequestDispatcher("/WEB-INF/jsp/useredit.jsp").forward(req, resp);
        }
    }

    private void handleUserList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(Constants.USER_SESSION_KEY);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jhtml");
            return;
        }

        if (!"ADMIN".equals(user.getRole())) {
            req.setAttribute("error", "пользователь не админ");
            req.getRequestDispatcher("/WEB-INF/jsp/welcome.jsp").forward(req, resp);
            return;
        }
        Collection<User> users = userService.getAllUsers();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/WEB-INF/jsp/userlist.jsp").forward(req, resp);


    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter(Constants.ACTION_PARAM);
        if (Constants.ACTION_LOGIN.equals(action)) {
            String login = req.getParameter(Constants.LOGIN_PARAM);
            String password = req.getParameter(Constants.PASSWORD_PARAM);

            User tempUser = new User(login, password, null, null, null, null, null, null);
            if (securityService.login(tempUser)) {
                User fullUser = userService.getUserByLogin(login);
                if (fullUser != null) {
                    req.getSession().setAttribute(Constants.USER_SESSION_KEY, fullUser);
                    resp.sendRedirect(req.getContextPath() + "/welcome.jhtml");
                } else {
                    req.setAttribute("error", "Ошибка загрузки пользователя");
                    req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
                }
            } else {
                req.setAttribute("error", "Неверный логин или пароль");
                req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
            }
        } else {
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
        }
    }

    private void handleWelcome(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter(Constants.ACTION_PARAM);
        if (Constants.ACTION_LOGOUT.equals(action)) {
            req.getSession().invalidate();
            resp.sendRedirect(req.getContextPath() + "/login.jhtml");
        } else {
            req.getRequestDispatcher("/WEB-INF/jsp/welcome.jsp").forward(req, resp);
        }
    }

    private void handleEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter(Constants.ACTION_PARAM);
        HttpSession httpSession = req.getSession();
        User user = (User) httpSession.getAttribute(Constants.USER_SESSION_KEY);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jhtml");
            return;
        }
        if (Constants.ACTION_CHANGE_PASSWORD.equals(action)) {
            String oldPassword = req.getParameter(Constants.OLD_PASSWORD_PARAM);
            String newPassword = req.getParameter(Constants.NEW_PASSWORD_PARAM);
            if (securityService.changePassword(user.getLogin(), oldPassword, newPassword)) {
                req.setAttribute("message", "пароль изменен");
            } else {
                req.setAttribute("error", "неправильный пароль");

            }

        }
        req.getRequestDispatcher("/WEB-INF/jsp/loginedit.jsp").forward(req, resp);
    }
}
