package service.factory;

import service.SecurityService;
import service.UserService;

public class ServiceFactory {
    private static final ServiceFactory INSTANCE = new ServiceFactory();

    private static final UserService userService = UserService.getInstance();
    private static final SecurityService securityService = SecurityService.getInstance();

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    private ServiceFactory() {
    }

    public SecurityService getSecurityService() {
        return securityService;
    }

    public UserService getUserService() {
        return userService;
    }

}
