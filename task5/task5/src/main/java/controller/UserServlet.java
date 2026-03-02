package controller;

import constants.Constants;
import model.Role;
import model.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@WebServlet({"/userlist.jhtml", "/useredit.jhtml", "/userdelete.jhtml"})
public class UserServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!checkAdmin(req, resp)) return;

        String servletPath = req.getServletPath();
        switch (servletPath) {
            case "/userlist.jhtml":
                handleUserList(req, resp);
                break;
            case "/useredit.jhtml":
                handleUserEdit(req, resp);
                break;
            case "/userdelete.jhtml":
                handleUserDelete(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!checkAdmin(req, resp)) return;

        String servletPath = req.getServletPath();
        if ("/useredit.jhtml".equals(servletPath)) {
            String action = req.getParameter(Constants.ACTION_PARAM);
            if (Constants.SAVE_PARAM.equals(action)) {
                handleUserSave(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }


    private boolean checkAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute(Constants.USER_SESSION_KEY);
        if (user.getRole() != Role.ADMIN) {
            req.setAttribute("error", "Недостаточно прав");
            req.getRequestDispatcher("/WEB-INF/jsp/welcome.jsp").forward(req, resp);
            return false;
        }
        return true;
    }


    private void handleUserList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<User> users = userService.getAllUsers();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/WEB-INF/jsp/userlist.jsp").forward(req, resp);
    }

    private void handleUserEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(Constants.LOGIN_PARAM);
        User editUser = null;
        if (login != null && !login.isEmpty()) {
            editUser = userService.getUserByLogin(login);
            if (editUser == null) {
                req.setAttribute("error", "Пользователь не найден");
                handleUserList(req, resp);
                return;
            }
        }
        req.setAttribute("user", editUser);
        req.getRequestDispatcher("/WEB-INF/jsp/useredit.jsp").forward(req, resp);
    }

    private void handleUserDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        if (login == null || login.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/userlist.jhtml");
            return;
        }

        User currentUser = (User) req.getSession().getAttribute(Constants.USER_SESSION_KEY);
        if (Objects.equals(login, currentUser.getLogin())) {
            req.setAttribute("error", "Нельзя удалить свою учётную запись");
            handleUserList(req, resp);
            return;
        }

        userService.deleteUser(login);
        resp.sendRedirect(req.getContextPath() + "/userlist.jhtml");
    }

    private void handleUserSave(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(Constants.LOGIN_PARAM);
        String password = req.getParameter(Constants.PASSWORD_PARAM);
        String email = req.getParameter(Constants.EMAIL_PARAM);
        String surname = req.getParameter(Constants.SURNAME_PARAM);
        String name = req.getParameter(Constants.NAME_PARAM);
        String patronymic = req.getParameter(Constants.PATRONYMIC_PARAM);
        String birthday = req.getParameter(Constants.BIRTHDAY_PARAM);
        String roleParam = req.getParameter(Constants.ROLE_PARAM);
        String originalLogin = req.getParameter("originalLogin");


        Map<String, String> errors = new HashMap<>();


        if (login == null || login.trim().isEmpty()) errors.put("login", "Логин обязателен");
        if (password == null || password.trim().isEmpty()) errors.put("password", "Пароль обязателен");
        if (name == null || name.trim().isEmpty()) errors.put("name", "Имя обязательно");
        if (email == null || email.trim().isEmpty()) errors.put("email", "Email обязателен");
        if (surname == null || surname.trim().isEmpty()) errors.put("surname", "Фамилия обязательна");
        if (birthday == null || birthday.trim().isEmpty()) errors.put("birthday", "Дата рождения обязательна");

        Role role = null;
        if (roleParam == null || roleParam.trim().isEmpty()) {
            errors.put("role", "Роль обязательна");
        } else {
            try {
                role = Role.valueOf(roleParam.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                errors.put("role", "Недопустимое значение роли");
            }
        }

        if (login != null && !login.trim().isEmpty()) {
            User existing = userService.getUserByLogin(login);
            if (existing != null && !login.equals(originalLogin)) {
                errors.put("login", "Пользователь с таким логином уже существует");
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
}