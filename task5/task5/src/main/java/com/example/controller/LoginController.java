package com.example.controller;

import com.example.constants.Constants;
import com.example.dto.LoginForm;
import com.example.model.User;
import com.example.service.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    private final SecurityService securityService;

    public LoginController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("/login.jhtml")
    public String loginForm(Model model) {
        model.addAttribute("loginForm",new LoginForm());
        return "login";
    }

    @PostMapping("/login.jhtml")
    public String login(@Valid  LoginForm loginForm,
                        BindingResult bindingResult,
                        HttpSession httpSession,
                        Model model
                        ) throws SQLException {
        if(bindingResult.hasErrors()){
            Map<String,String> errors=new HashMap<>();
            for(FieldError error:bindingResult.getFieldErrors()){
                errors.put(error.getField(),error.getDefaultMessage());
            }
            model.addAttribute("errors",errors);
            return "login";
        }

        User user = securityService.login(loginForm.getLogin(), loginForm.getPassword());
        if (user != null) {
            httpSession.setAttribute(Constants.USER_SESSION_KEY, user);
            return "redirect:/welcome.jhtml";
        } else {
            model.addAttribute("error","неверный логин или пароль");
            return "login";
        }
    }
}
