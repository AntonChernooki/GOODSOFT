package controller;

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
        String contextPath = req.getContextPath();

        if (servletPath.equals("/login.jhtml")) {
            String action = req.getParameter("action");
            if ("login".equals(action)) {
                String login = req.getParameter("login");
                String password = req.getParameter("password");

                User user = new User(login, password);
                if (securityService.login(user)) {
                    req.getSession().setAttribute("userData", user);
                    resp.sendRedirect(contextPath + "/welcome.jhtml");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/login.jhtml");
                }
            } else {
                req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
            }
            return;
        }


        if (servletPath.equals("/welcome.jhtml")) {
            String action = req.getParameter("action");
            if ("logout".equals(action)) {
                req.getSession().invalidate();
                resp.sendRedirect(req.getContextPath() + "/login.jhtml");
            } else {
                req.getRequestDispatcher("/WEB-INF/jsp/welcome.jsp").forward(req, resp);
            }
            return;
        }



        if (servletPath.equals("/loginedit.jhtml")) {
            String action = req.getParameter("action");
            HttpSession httpSession=req.getSession();
            User user= (User) httpSession.getAttribute("userData");
            if("changePassword".equals(action)){
                String oldPassword= req.getParameter("oldPassword");
                String newPassword= req.getParameter("newPassword");
                if(securityService.changePassword(user.getUsername(),oldPassword,newPassword)){
                    req.setAttribute("message","пароль изменен");
                }else {
                    req.setAttribute("error","неправильный пароль");

                }

            }
            req.getRequestDispatcher("/WEB-INF/jsp/loginedit.jsp").forward(req, resp);
            return;
        }
    }
}
