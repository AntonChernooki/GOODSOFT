package listener;

import dao.impl.InMemoryUserDao;
import dao.UserDao;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        UserDao userDao = InMemoryUserDao.getInstance();
        sce.getServletContext().setAttribute("userDao", userDao);
    }


    public void contextDestroyed(ServletContextEvent sce) {
    }
}
