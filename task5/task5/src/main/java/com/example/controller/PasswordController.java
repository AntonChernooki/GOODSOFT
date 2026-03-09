package com.example.controller;

import com.example.constants.Constants;
import com.example.dto.PasswordChangeForm;
import com.example.model.User;
import com.example.service.SecurityService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
public class PasswordController {
    private final SecurityService securityService;

    public PasswordController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("/loginedit.jhtml")
    public String passwordForm(Model model) {
        model.addAttribute("passwordChangeForm", new PasswordChangeForm());
        return "loginedit";
    }

    @PostMapping("/loginedit.jhtml")
    public String changePassword(@Valid PasswordChangeForm passwordChangeForm,
                                 BindingResult bindingResult,
                                 HttpSession httpSession,
                                 Model model) {
        User user = (User) httpSession.getAttribute(Constants.USER_SESSION_KEY);
        if (user == null) {
            return "redirect:/login.jhtml";
        }
        if (bindingResult.hasErrors()) {
            return "loginedit";
        }


        if (securityService.changePassword(user.getLogin(), passwordChangeForm.getOldPassword(), passwordChangeForm.getNewPassword())) {
            model.addAttribute("message", "Пароль изменён");
            return "loginedit";
        } else {
            model.addAttribute("error", "неверный пароль");
            return "loginedit";
        }

    }

}
