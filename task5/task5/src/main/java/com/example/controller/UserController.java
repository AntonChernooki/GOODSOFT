package com.example.controller;

import com.example.dto.UserDto;
import com.example.dto.request.AddUserRequest;
import com.example.dto.request.UserRequest;
import com.example.dto.response.ErrorResponse;
import com.example.model.User;
import com.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
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
        try {
            Collection<User> users = userService.getAllUsers();
            List<UserDto> userDtos = new ArrayList<>();
            for (User user : users) {
                userDtos.add(new UserDto(user));
            }
            return ResponseEntity.ok(userDtos);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{login}")
    public ResponseEntity<?> getUserByLogin(@PathVariable String login) {

        try {
            User user = userService.getUserByLogin(login);
            if (user != null) {
                return ResponseEntity.ok(new UserDto(user));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("error", "не нашелся пользователь"));
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid AddUserRequest addUserRequest) {
        Map<String, String> errors = new HashMap<>();
        if (userService.addUser(
                addUserRequest.getLogin(),
                addUserRequest.getPassword(),
                addUserRequest.getEmail(),
                addUserRequest.getSurname(),
                addUserRequest.getName(),
                addUserRequest.getPatronymic(),
                addUserRequest.getBirthday(),
                addUserRequest.getRoles(),
                errors)) {

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
        }
    }

    @PutMapping("/{originalLogin}")
    public ResponseEntity<?> updateUser(@PathVariable String originalLogin, @RequestBody @Valid UserRequest userRequest) {
        Map<String, String> errors = new HashMap<>();
        if (userService.updateUser(
                originalLogin,
                userRequest.getLogin(),
                userRequest.getPassword(),
                userRequest.getEmail(),
                userRequest.getSurname(),
                userRequest.getName(),
                userRequest.getPatronymic(),
                userRequest.getBirthday(),
                userRequest.getRoles(),
                errors)) {
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
        }
    }

    @DeleteMapping("/{login}")
    public ResponseEntity<?> deleteUser(@PathVariable String login) {

      if(  userService.deleteUser(login)){
          return ResponseEntity.ok().build();
      }else {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("error", "не получилось удалить пользователя"));
      }
    }

}
