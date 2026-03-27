package com.example.Autobase.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class UserLoginDto {

    @NotBlank(message = "логин обязателен")
    private String login;
    @NotBlank(message = "пароль обязателен")
    @Size(min = 4, message = "пароль должен быть минимум 4 символа")
    private String password;


    public UserLoginDto(String login, String password, Set<String> roles) {
        this.login = login;
        this.password = password;

    }

}
