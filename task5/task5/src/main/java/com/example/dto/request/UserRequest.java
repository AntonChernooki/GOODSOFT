package com.example.dto.request;


import com.example.model.Role;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

public class UserRequest {
    @NotBlank(message = "{user.login.blank}")
    private String login;

    @NotBlank(message = "{user.password.blank}")
    @Size(min = 4, message = "{user.password.size}")
    private String password;

    @NotBlank(message = "{user.email.blank}")
    @Email(message = "{user.email.invalid}")
    private String email;

    @NotBlank(message = "{user.surname.blank}")
    private String surname;

    @NotBlank(message = "{user.name.blank}")
    private String name;

    private String patronymic;

    @NotNull(message = "{user.birthday.null}")
    @Past(message = "{user.birthday.past}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @NotEmpty(message = "{user.roles.notEmpty}")
    private Set<Role> roles;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
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
}