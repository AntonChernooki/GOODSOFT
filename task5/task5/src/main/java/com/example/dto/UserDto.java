package com.example.dto;

import com.example.model.Role;
import com.example.model.User;

import java.time.LocalDate;
import java.util.Set;

public class UserDto {
    private String login;
    private String email;
    private String surname;
    private String name;
    private String patronymic;
    private LocalDate birthday;
    private Set<Role> roles;
    public UserDto(User user) {
        this.login = user.getLogin();
        this.email = user.getEmail();
        this.surname = user.getSurname();
        this.name = user.getName();
        this.patronymic = user.getPatronymic();
        this.birthday = user.getBirthday();
        this.roles = user.getRoles();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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