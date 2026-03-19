package com.example.controller;

import com.example.dto.LoginForm;
import com.example.dto.UserDto;
import com.example.dto.request.ChangePasswordRequest;
import com.example.model.User;
import com.example.service.SecurityService;
import com.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final SecurityService securityService;


    public AuthController(SecurityService securityService, UserService userService) {
        this.securityService = securityService;

    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid LoginForm loginForm)  {
        User user = securityService.login(loginForm.getLogin(), loginForm.getPassword());
        return ResponseEntity.ok(new UserDto(user));

    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
        securityService.changePassword(
                changePasswordRequest.getLogin(),
                changePasswordRequest.getOldPassword(),
                changePasswordRequest.getNewPassword());
            return ResponseEntity.ok().build();

    }
}
