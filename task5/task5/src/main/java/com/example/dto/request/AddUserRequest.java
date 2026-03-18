package com.example.dto.request;

import com.example.model.Role;
import com.example.model.User;

import java.time.LocalDate;
import java.util.Set;

public class AddUserRequest {
    private String login;
    private String email;
    private String surname;
    private String name;
    private String patronymic;
    private LocalDate birthday;
    private Set<Role> roles;
    private String password;

    public AddUserRequest(String login, String email, String name, String surname, LocalDate birthday, String patronymic, Set<Role> roles, String password) {
        this.login = login;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.patronymic = patronymic;
        this.roles = roles;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}