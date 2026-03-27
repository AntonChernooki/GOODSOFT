package com.example.Autobase.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class UserUpdateDto {

    private String login;

    @Size(min = 4)
    private String password;

    private Boolean enabled;
    private Set<String> roles;
}

