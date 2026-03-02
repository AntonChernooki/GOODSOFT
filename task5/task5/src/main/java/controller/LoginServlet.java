package controller;

import constants.Constants;
import dao.impl.InMemoryUserDao;
import dao.UserDao;
import model.User;
import service.SecurityService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login.jhtml")
public class LoginServlet extends HttpServlet {
    private SecurityService securityService;
    private UserService userService;

    public void init() throws ServletException {
        UserDao userDao = (UserDao) getServletContext().getAttribute("userDao");
        this.securityService = new SecurityService(userDao);
        this.userService = new UserService(userDao);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleLogin(req, resp);
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(Constants.LOGIN_PARAM);
        String password = req.getParameter(Constants.PASSWORD_PARAM);

        User tempUser = new User(login, password, null, null, null, null, null, null);
        if (securityService.login(tempUser)) {
            User user = userService.getUserByLogin(login);
            if (user != null) {
                req.getSession().setAttribute(Constants.USER_SESSION_KEY, user);
                resp.sendRedirect(req.getContextPath() + "/welcome.jhtml");
            } else {
                req.setAttribute("error", "Ошибка загрузки пользователя");
                req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("error", "Неверный логин или пароль");
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
        }
    }
}