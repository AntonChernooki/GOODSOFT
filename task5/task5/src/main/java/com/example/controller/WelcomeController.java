package com.example.controller;

import com.example.constants.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller

public class WelcomeController {
    @GetMapping("/welcome.jhtml")
    public String welcome() {

        return "welcome";
    }
}
