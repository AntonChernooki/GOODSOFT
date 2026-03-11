package com.example.controller;

import com.example.dto.LoginForm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.validation.Valid;
import java.sql.SQLException;


@Controller
public class LoginController {


    private final AuthenticationManager authenticationManager;

    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/login.jhtml")
    public String loginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        model.addAttribute("loginForm", new LoginForm());
        if (error != null) {
            model.addAttribute("error", "Неверный логин или пароль");
        }
        return "login";
    }

    @PostMapping("/login.jhtml")
    public String login(@Valid LoginForm loginForm,
                        BindingResult bindingResult,
                        Model model
    ) throws SQLException {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getLogin(), loginForm.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/welcome.jhtml";
        } catch (AuthenticationException e) {
            model.addAttribute("error", "Неверный логин или пароль");
            return "login";
        }

    }
}
