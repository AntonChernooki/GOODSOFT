package com.example.Autobase.dto.response;

import lombok.Data;


import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserResponseDto {
    private Long id;
    private String login;
    private Boolean enabled;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private Set<String> roles;



    public UserResponseDto() {
    }

}
