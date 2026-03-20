package com.example.controller;

import com.example.dto.LoginForm;
import com.example.dto.UserDto;
import com.example.dto.request.ChangePasswordRequest;
import com.example.dto.response.JwtResponse;
import com.example.model.User;
import com.example.security.JwtUtils;
import com.example.service.SecurityService;
import com.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final SecurityService securityService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;


    public AuthController(SecurityService securityService, UserService userService, JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.securityService = securityService;

        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody @Valid LoginForm loginForm) {
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getLogin(),loginForm.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt=jwtUtils.generateJwtToken(authentication);
        String login=authentication.getName();
       // User user = securityService.login(loginForm.getLogin(), loginForm.getPassword());
        return ResponseEntity.ok(new JwtResponse(jwt,login));

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
