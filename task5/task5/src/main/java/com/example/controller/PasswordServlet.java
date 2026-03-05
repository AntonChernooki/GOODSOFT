package com.example.controller;

import com.example.constants.Constants;
import com.example.model.User;
import com.example.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;



import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class PasswordServlet extends HttpServlet {

    @Autowired
    private SecurityService securityService;

public PasswordServlet(SecurityService securityService){
    this.securityService=securityService;
}


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/loginedit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            handleEdit(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        User user = (User) req.getSession().getAttribute(Constants.USER_SESSION_KEY);

        String action = req.getParameter(Constants.ACTION_PARAM);
        if (Constants.ACTION_CHANGE_PASSWORD.equals(action)) {
            String oldPassword = req.getParameter(Constants.OLD_PASSWORD_PARAM);
            String newPassword = req.getParameter(Constants.NEW_PASSWORD_PARAM);

            if (oldPassword == null || oldPassword.trim().isEmpty() ||
                    newPassword == null || newPassword.trim().isEmpty()) {
                req.setAttribute("error", "Поля не могут быть пустыми");
            } else if (securityService.changePassword(user.getLogin(), oldPassword, newPassword)) {
                req.setAttribute("message", "Пароль изменён");
            } else {
                req.setAttribute("error", "Неправильный старый пароль");
            }
        }
        req.getRequestDispatcher("/WEB-INF/jsp/loginedit.jsp").forward(req, resp);
    }
}
