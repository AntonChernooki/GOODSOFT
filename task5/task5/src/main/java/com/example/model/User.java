package com.example.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class User {
    private String login;
    private String password;
    private String email;
    private String surname;
    private String name;
    private String patronymic;
    private LocalDate birthday;
    private Set<Role> roles;

    public User(String login, String password, String email, String surname, String name, String patronymic, LocalDate birthday, Set<Role> roles) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.roles = roles;
    }
    public User(){

    }

    public User(String login, Set<Role> roles) {
        this.login=login;
        this.roles=roles;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public boolean hasRole(Role role){
        return roles.contains(role);
    }
    public boolean isAdmin() {
        return hasRole(Role.ADMIN);
    }
}
