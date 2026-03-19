package com.example.controller;

import com.example.dto.UserDto;
import com.example.dto.request.AddUserRequest;
import com.example.dto.request.UserRequest;
import com.example.model.User;
import com.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {

        Collection<User> users = userService.getAllUsers();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(new UserDto(user));
        }
        return ResponseEntity.ok(userDtos);

    }

    @GetMapping("/{login}")
    public ResponseEntity<UserDto> getUserByLogin(@PathVariable String login) {
        User user = userService.getUserByLogin(login);
        return ResponseEntity.ok(new UserDto(user));
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid AddUserRequest addUserRequest) {

        userService.addUser(
                addUserRequest.getLogin(),
                addUserRequest.getPassword(),
                addUserRequest.getEmail(),
                addUserRequest.getSurname(),
                addUserRequest.getName(),
                addUserRequest.getPatronymic(),
                addUserRequest.getBirthday(),
                addUserRequest.getRoles());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping("/{originalLogin}")
    public ResponseEntity<Void> updateUser(@PathVariable String originalLogin, @RequestBody @Valid UserRequest userRequest) {

        userService.updateUser(
                originalLogin,
                userRequest.getLogin(),
                userRequest.getPassword(),
                userRequest.getEmail(),
                userRequest.getSurname(),
                userRequest.getName(),
                userRequest.getPatronymic(),
                userRequest.getBirthday(),
                userRequest.getRoles());
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/{login}")
    public ResponseEntity<Void> deleteUser(@PathVariable String login) {

        userService.deleteUser(login);
        return ResponseEntity.ok().build();

    }

}
