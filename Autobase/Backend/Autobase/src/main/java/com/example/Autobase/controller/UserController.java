package com.example.Autobase.controller;

import com.example.Autobase.dto.request.user.UserLoginDto;
import com.example.Autobase.dto.request.user.UserRegistrationDto;
import com.example.Autobase.dto.request.user.UserSetEnabledDto;
import com.example.Autobase.dto.request.user.UserUpdateDto;
import com.example.Autobase.dto.response.user.LoginResponseDto;
import com.example.Autobase.dto.response.user.UserResponseDto;
import com.example.Autobase.security.JwtUtils;
import com.example.Autobase.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/login/{login}")
    @PreAuthorize("hasRole('ADMIN') or #login == authentication.principal.username")
    public ResponseEntity<UserResponseDto> getUserByLogin(@PathVariable("login") String login) {
        UserResponseDto userResponseDto = userService.getUserByLogin(login);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @GetMapping("/id/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("id") Long id) {
        UserResponseDto userResponseDto = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> userResponseDtoList = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDtoList);
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDto> registerUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        userService.registerUser(userRegistrationDto);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userRegistrationDto.getLogin(), userRegistrationDto.getPassword()));
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserResponseDto userResponseDto = userService.getUserByLogin(userRegistrationDto.getLogin());
        LoginResponseDto loginResponseDto = new LoginResponseDto(jwt, userResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(loginResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody UserLoginDto userLoginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDto.getLogin(), userLoginDto.getPassword()));

        String jwt = jwtUtils.generateJwtToken(authentication);

        UserResponseDto userResponseDto = userService.getUserByLogin(userLoginDto.getLogin());
        LoginResponseDto loginResponseDto = new LoginResponseDto(jwt, userResponseDto);

        return ResponseEntity.status(HttpStatus.OK).body(loginResponseDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/enabled")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDto> setUserEnabled(@PathVariable("id") Long id,
            @Valid @RequestBody UserSetEnabledDto userSetEnabledDto) {
        UserResponseDto userResponseDto = userService.setUserEnabled(id, userSetEnabledDto);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable("id") Long id,
            @Valid @RequestBody UserUpdateDto userUpdateDto) {
        UserResponseDto userResponseDto = userService.updateUser(id, userUpdateDto);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }
}
