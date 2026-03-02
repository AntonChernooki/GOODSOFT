package controller;

import constants.Constants;
import dao.UserDao;
import model.Role;
import model.User;
import service.SecurityService;
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

    private UserService userService;

    public void init() throws ServletException {
        UserDao userDao = (UserDao) getServletContext().getAttribute("userDao");

        this.userService = new UserService(userDao);
    }

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
        boolean success;
        if (originalLogin == null || originalLogin.isEmpty()) {
            success = userService.addUser(login, password, email, surname, name,
                    patronymic, birthday, roleParam, errors);
        } else {
            success = userService.updateUser(originalLogin, login, password, email, surname, name,
                    patronymic, birthday, roleParam, errors);
        }

        if (success) {
            resp.sendRedirect(req.getContextPath() + "/userlist.jhtml");
        } else {
            Role role = null;
            if (roleParam != null && !roleParam.isEmpty()) {
                try {
                    role = Role.valueOf(roleParam.trim().toUpperCase());
                } catch (IllegalArgumentException ignored) {
                }
            }
            User tempUser = new User(login, password, email, surname, name, patronymic, birthday, role);
            req.setAttribute("user", tempUser);
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/WEB-INF/jsp/useredit.jsp").forward(req, resp);
        }
    }
}
