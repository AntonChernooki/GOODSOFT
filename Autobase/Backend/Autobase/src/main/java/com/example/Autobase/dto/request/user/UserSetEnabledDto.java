package com.example.Autobase.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserSetEnabledDto {
    @NotBlank(message = "enabled обязателен")
    String enabled;
}
