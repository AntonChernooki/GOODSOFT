package com.example.Autobase.dto.request.driver;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class DriverUpdateDto {
    private String name;
    private String phone;
    @PositiveOrZero(message = "Опыт вождения должен быть неотрицательным")
    private Integer experienceYears;
    private String notes;
    private String status;
}