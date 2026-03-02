package controller;

import constants.Constants;
import dao.impl.UserDao;
import model.User;
import service.SecurityService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/loginedit.jhtml")
public class PasswordServlet extends HttpServlet {
    private  SecurityService securityService;


    public void init()throws ServletException
    {
        UserDao userDao = (UserDao) getServletContext().getAttribute("userDao");
        this.securityService = new SecurityService(userDao);

    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/loginedit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleEdit(req, resp);
    }

    private void handleEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
