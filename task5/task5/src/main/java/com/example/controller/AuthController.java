package com.example.controller;

import com.example.dto.LoginForm;
import com.example.dto.UserDto;
import com.example.dto.request.ChangePasswordRequest;
import com.example.dto.response.ErrorResponse;
import com.example.model.User;
import com.example.service.SecurityService;
import com.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.SQLException;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final SecurityService securityService;


    public AuthController(SecurityService securityService, UserService userService) {
        this.securityService = securityService;

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginForm loginForm) {
        try {
            User user = securityService.login(loginForm.getLogin(), loginForm.getPassword());
            if (user != null) {
                return ResponseEntity.ok(new UserDto(user));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("error", "Неверный логин или пароль"));
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("error", "Ошибка бд"));
        }
    }

    @PostMapping("/loginedit")
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
        if (securityService.changePassword(
                changePasswordRequest.getLogin(),
                changePasswordRequest.getOldPassword(),
                changePasswordRequest.getNewPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body(new ErrorResponse("error", "Неверный пароль"));
        }
    }
}
