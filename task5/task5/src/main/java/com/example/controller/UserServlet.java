package com.example.controller;

import com.example.constants.Constants;
import com.example.model.Role;
import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet({"/userlist.jhtml", "/useredit.jhtml", "/userdelete.jhtml"})
public class UserServlet extends HttpServlet {

@Autowired
    private UserService userService;

   public UserServlet(UserService userService){
       this.userService=userService;
   }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!checkAdmin(req, resp)) return;

        String servletPath = req.getServletPath();
        switch (servletPath) {
            case "/userlist.jhtml":
                try {
                    handleUserList(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "/useredit.jhtml":
                try {
                    handleUserEdit(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "/userdelete.jhtml":
                try {
                    handleUserDelete(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
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
                try {
                    handleUserSave(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }


    private boolean checkAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute(Constants.USER_SESSION_KEY);
        if (user == null || !user.getRoles().contains(Role.ADMIN)) {
            req.setAttribute("error", "Недостаточно прав");
            req.getRequestDispatcher("/WEB-INF/jsp/welcome.jsp").forward(req, resp);
            return false;
        }
        return true;
    }


    private void handleUserList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        Collection<User> users = userService.getAllUsers();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/WEB-INF/jsp/userlist.jsp").forward(req, resp);
    }

    private void handleUserEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        String login = req.getParameter(Constants.LOGIN_PARAM);
        User editUser = null;
        req.setAttribute("allRoles", Role.values());
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

    private void handleUserDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
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

    private void handleUserSave(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        String login = req.getParameter(Constants.LOGIN_PARAM);
        String password = req.getParameter(Constants.PASSWORD_PARAM);
        String email = req.getParameter(Constants.EMAIL_PARAM);
        String surname = req.getParameter(Constants.SURNAME_PARAM);
        String name = req.getParameter(Constants.NAME_PARAM);
        String patronymic = req.getParameter(Constants.PATRONYMIC_PARAM);
        String birthday = req.getParameter(Constants.BIRTHDAY_PARAM);
        //String roleParam = req.getParameter(Constants.ROLE_PARAM);
        String originalLogin = req.getParameter("originalLogin");
        String[] roleParams = req.getParameterValues(Constants.ROLES_PARAM);

        Set<Role> roles = new HashSet<>();
        if (roleParams != null) {
            for (String r : roleParams) {
                try {
                    roles.add(Role.valueOf(r.trim().toUpperCase()));
                } catch (IllegalArgumentException ignored) {
                }
            }
        }

        Map<String, String> errors = new HashMap<>();
        boolean success;
        if (originalLogin == null || originalLogin.isEmpty()) {
            success = userService.addUser(login, password, email, surname, name,
                    patronymic, birthday, roles, errors);
        } else {
            success = userService.updateUser(originalLogin, login, password, email, surname, name,
                    patronymic, birthday, roles, errors);
        }

        if (success) {
            resp.sendRedirect(req.getContextPath() + "/userlist.jhtml");
        } else {
            /*Role role = null;
            if (roleParam != null && !roleParam.isEmpty()) {
                try {
                    role = Role.valueOf(roleParam.trim().toUpperCase());
                } catch (IllegalArgumentException ignored) {
                }
            }*/
            User tempUser = new User(login, password, email, surname, name, patronymic, birthday, roles);
            req.setAttribute("user", tempUser);
            req.setAttribute("errors", errors);
            req.setAttribute("allRoles", Role.values());
            req.getRequestDispatcher("/WEB-INF/jsp/useredit.jsp").forward(req, resp);
        }
    }
}
