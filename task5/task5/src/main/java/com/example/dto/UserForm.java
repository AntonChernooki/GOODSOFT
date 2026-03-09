package com.example.dto;

import com.example.model.Role;
import com.example.model.User;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class UserForm {
    @NotBlank(message = "Логин обязателен")
    private String login;

    @NotBlank(message = "Пароль обязателен")
    @Size(min = 4, message = "Пароль должен содержать минимум 4 символа")
    private String password;

    @NotBlank(message = "Email обязателен")
    @Email(message = "Некорректный email")
    private String email;

    @NotBlank(message = "Фамилия обязательна")
    private String surname;

    @NotBlank(message = "Имя обязательно")
    private String name;

    private String patronymic;

    @NotNull(message = "Дата рождения обязательна")
    @Past(message = "Дата рождения должна быть в прошлом")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @NotEmpty(message = "Роль обязательна")
    private Set<Role> roles;

    private String originalLogin;


    public UserForm(User user, String originalLogin) {
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.surname = user.getSurname();
        this.name = user.getName();
        this.patronymic = user.getPatronymic();
        this.birthday = user.getBirthday();
        this.roles = user.getRoles();
        this.originalLogin = originalLogin;
    }


    public UserForm(String login, String password, String email, String surname, String name, String patronymic, LocalDate birthday, Set<Role> roles, String originalLogin) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.roles = roles;
        this.originalLogin = originalLogin;
    }

    public UserForm() {

    }

    public void setRoles(String[] roleNames) {
        if (roleNames == null) {
            this.roles = new HashSet<>();
        } else {
            this.roles = Arrays.stream(roleNames).map(x -> x.toUpperCase()).map(x -> Role.valueOf(x)).collect(Collectors.toSet());
        }
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getOriginalLogin() {
        return originalLogin;
    }

    public void setOriginalLogin(String originalLogin) {
        this.originalLogin = originalLogin;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }
}

