package filter;

import constants.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String path = req.getServletPath();
        HttpSession session = req.getSession(false);
        boolean haveLogin = (session != null && session.getAttribute(Constants.USER_SESSION_KEY) != null);
        if (path.equals("/login.jhtml")) {
            chain.doFilter(request, response);
            return;
        }
        if (!haveLogin) {
            resp.sendRedirect(req.getContextPath() + "/login.jhtml");
            return;
        }
        chain.doFilter(request, response);


    }

    @Override
    public void destroy() {

    }

}
