package controller;

import constants.Constants;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/welcome.jhtml")
public class WelcomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleWelcome(req, resp);
    }

    private void handleWelcome(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter(Constants.ACTION_PARAM);
        if (Constants.ACTION_LOGOUT.equals(action)) {
            req.getSession().invalidate();
            resp.sendRedirect(req.getContextPath() + "/login.jhtml");
        } else {
            req.getRequestDispatcher("/WEB-INF/jsp/welcome.jsp").forward(req, resp);
        }
    }
}