package com.example.controller;

import com.example.constants.Constants;
import com.example.dto.UserForm;
import com.example.model.Role;
import com.example.model.User;
import com.example.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private boolean isAdmin(HttpSession session) {
        User user = (User) session.getAttribute(Constants.USER_SESSION_KEY);
        return user != null && user.getRoles().contains(Role.ADMIN);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/userlist.jhtml")
    public String userListForm(Model model) throws SQLException {

        model.addAttribute("users", userService.getAllUsers());
        return "userlist";

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/useredit.jhtml")
    public String editUser(@RequestParam(required = false) String login,
                           Model model
    ) throws SQLException {


        UserForm userForm;
        if (login != null && !login.isEmpty()) {
            User editUser = userService.getUserByLogin(login);
            if (editUser == null) {
                model.addAttribute("error", "Пользователь не найден");
                return "redirect:/userlist.jhtml";
            }
            userForm = new UserForm(editUser, editUser.getLogin());
        } else {
            userForm = new UserForm();
            userForm.setOriginalLogin(null);
        }
        model.addAttribute("user", userForm);
        model.addAttribute("allRoles", Role.values());
        return "useredit";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/userdelete.jhtml")
    public String deleteUser(@RequestParam String login,

                             Model model) throws SQLException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (login.equals(authentication.getName())) {
            model.addAttribute("error", "Нельзя удалить свою учётную запись");
            return "redirect:/userlist.jhtml";
        }
        userService.deleteUser(login);
        return "redirect:/userlist.jhtml";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/useredit.jhtml")
    public String saveUser(@Valid @ModelAttribute("user") UserForm userForm,
                           BindingResult bindingResult,
                           Model model) throws SQLException {


        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", Role.values());
            return "useredit";
        }


        Set<Role> roles = userForm.getRoles();
        if (roles == null) {
            roles = new HashSet<>();
        }

        boolean success;
        Map<String, String> serviceErrors = new HashMap<>();
        if (userForm.getOriginalLogin() == null || userForm.getOriginalLogin().isEmpty()) {
            success = userService.addUser(userForm.getLogin(), userForm.getPassword(), userForm.getEmail(),
                    userForm.getSurname(), userForm.getName(), userForm.getPatronymic(), userForm.getBirthday(), roles, serviceErrors);
        } else {
            success = userService.updateUser(
                    userForm.getOriginalLogin(), userForm.getLogin(), userForm.getPassword(), userForm.getEmail(), userForm.getSurname(), userForm.getName(),
                    userForm.getPatronymic(), userForm.getBirthday(), roles, serviceErrors);
        }

        if (success) {
            return "redirect:/userlist.jhtml";
        } else {
            model.addAttribute("errors", serviceErrors);
            model.addAttribute("allRoles", Role.values());
            return "useredit";
        }
    }
}