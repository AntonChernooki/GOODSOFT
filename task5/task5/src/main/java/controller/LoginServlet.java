package controller;

import constants.Constants;
import dao.UserDao;
import model.User;
import service.SecurityService;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login.jhtml")
public class LoginServlet extends HttpServlet {
    private SecurityService securityService;

    public void init() throws ServletException {
        UserDao userDao = (UserDao) getServletContext().getAttribute("userDao");
        this.securityService = new SecurityService(userDao);
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

        User user = securityService.login(login, password);

        if (user != null) {
            req.getSession().setAttribute(Constants.USER_SESSION_KEY, user);
            resp.sendRedirect(req.getContextPath() + "/welcome.jhtml");
        } else {
            req.setAttribute("error", "Неверный логин или пароль");
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
        }
    }
}