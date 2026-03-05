package service.factory;

import dao.UserDao;
import dao.factory.JdbcDaoFactory;
import dao.impl.JdbcUserDao;
import service.SecurityService;
import service.UserService;

public class ServiceFactory {
    private static final ServiceFactory INSTANCE = new ServiceFactory();

    private final UserDao userDao;
    private  final UserService userService;
    private  final SecurityService securityService;

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    private ServiceFactory() {
        this.userDao= JdbcDaoFactory.getInstance().getUserDao();
        this.userService=new UserService(userDao);
        this.securityService=new SecurityService(userDao);
    }

    public SecurityService getSecurityService() {
        return securityService;
    }

    public UserService getUserService() {
        return userService;
    }

}
