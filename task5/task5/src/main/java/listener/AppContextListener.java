package listener;


import service.factory.ServiceFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();

        sce.getServletContext().setAttribute("serviceFactory", serviceFactory);

    }


    public void contextDestroyed(ServletContextEvent sce) {
    }
}
