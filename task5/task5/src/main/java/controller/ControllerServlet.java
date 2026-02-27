package controller;

import constants.Constants;
import model.User;
import service.SecurityService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ControllerServlet extends HttpServlet {
    private final SecurityService securityService = new SecurityService();

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
            default: {
                resp.sendRedirect(req.getContextPath() + "/welcome.jhtml");
            }
        }


    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter(Constants.ACTION_PARAM);
        if (Constants.ACTION_LOGIN.equals(action)) {
            String username = req.getParameter(Constants.USERNAME_PARAM);
            String password = req.getParameter(Constants.PASSWORD_PARAM);

            User user = new User(username, password);
            if (securityService.login(user)) {
                req.getSession().setAttribute(Constants.USER_SESSION_KEY, user);
                resp.sendRedirect(req.getContextPath() + "/welcome.jhtml");
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
            if (securityService.changePassword(user.getUsername(), oldPassword, newPassword)) {
                req.setAttribute("message", "пароль изменен");
            } else {
                req.setAttribute("error", "неправильный пароль");

            }

        }
        req.getRequestDispatcher("/WEB-INF/jsp/loginedit.jsp").forward(req, resp);
    }
}
