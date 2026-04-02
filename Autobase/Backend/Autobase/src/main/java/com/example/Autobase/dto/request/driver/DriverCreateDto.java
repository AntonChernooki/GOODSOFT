package com.example.Autobase.dto.request.driver;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class DriverCreateDto {
    @NotNull(message = "User id обязателен")
    private Long userId;
    @NotBlank(message = "имя водителя обязательно")
    private String name;
    @NotBlank(message = "телефон обязателен")
    private String phone;
    @PositiveOrZero(message = "опыт вождения должен быть неотрицательным")
    private Integer experienceYears;
    private String notes;

    public DriverCreateDto(Long userId) {
        this.userId = userId;
    }

    public DriverCreateDto() {

    }

    public DriverCreateDto(Long userId, String name, Integer experienceYears, String phone, String notes) {
        this.userId = userId;
        this.name = name;
        this.experienceYears = experienceYears;
        this.phone = phone;
        this.notes = notes;
    }

}
