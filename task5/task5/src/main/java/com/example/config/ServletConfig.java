
/*package com.example.config;

import com.example.controller.*;
import com.example.service.SecurityService;
import com.example.service.UserService;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfig {

    @Bean
    public LoginServlet loginServlet(SecurityService securityService) {
        return new LoginServlet(securityService);
    }

    @Bean
    public ServletRegistrationBean<LoginServlet> loginServletRegistration(LoginServlet loginServlet) {
        ServletRegistrationBean<LoginServlet> registration = new ServletRegistrationBean<>(loginServlet, "/login.jhtml");
        registration.setLoadOnStartup(1);
        return registration;
    }

    @Bean
    public PasswordServlet passwordServlet(SecurityService securityService) {
        return new PasswordServlet(securityService);
    }

    @Bean
    public ServletRegistrationBean<PasswordServlet> passwordServletRegistration(PasswordServlet passwordServlet) {
        ServletRegistrationBean<PasswordServlet> registration = new ServletRegistrationBean<>(passwordServlet, "/loginedit.jhtml");
        registration.setLoadOnStartup(1);
        return registration;
    }

    @Bean
    public UserServlet userServlet(UserService userService) {
        return new UserServlet(userService);
    }

    @Bean
    public ServletRegistrationBean<UserServlet> userServletRegistration(UserServlet userServlet) {
        ServletRegistrationBean<UserServlet> registration = new ServletRegistrationBean<>(
                userServlet, "/userlist.jhtml", "/useredit.jhtml", "/userdelete.jhtml");
        registration.setLoadOnStartup(1);
        return registration;
    }

    @Bean
    public WelcomeServlet welcomeServlet() {
        return new WelcomeServlet();
    }

    @Bean
    public ServletRegistrationBean<WelcomeServlet> welcomeServletRegistration(WelcomeServlet welcomeServlet) {
        ServletRegistrationBean<WelcomeServlet> registration = new ServletRegistrationBean<>(welcomeServlet, "/welcome.jhtml");
        registration.setLoadOnStartup(1);
        return registration;
    }
}
*/


