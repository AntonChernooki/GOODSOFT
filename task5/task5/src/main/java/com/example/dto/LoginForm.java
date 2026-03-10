package com.example.dto;

import javax.validation.constraints.NotBlank;

public class LoginForm {
    @NotBlank(message = "{login.blank}")
    private String login;

    @NotBlank(message = "{password.blank}")
    private String password;

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
}
