package com.example.Autobase.dto.response.user;

import lombok.Data;

@Data
public class LoginResponseDto {
    private String token;
    private UserResponseDto user;


    public LoginResponseDto(String token, UserResponseDto user) {
        this.token = token;
        this.user = user;
    }
}
