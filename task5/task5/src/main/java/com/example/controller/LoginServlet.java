package com.example.controller;

import com.example.constants.Constants;
import com.example.model.User;
import com.example.service.SecurityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.*;



import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {

    @Autowired
    private  SecurityService securityService;

    public LoginServlet(SecurityService securityService) {
        this.securityService = securityService;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            handleLogin(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
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