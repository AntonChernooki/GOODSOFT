package com.example.Autobase.dto.request.driver;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DriverStatusUpdateDto {
    @NotBlank(message = "Status is required")
    private String status;
}
