package com.example.Autobase.dto.request.car;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CarCreateDto {
    @NotBlank(message = "модель обязательна")
    private String model;

    @NotBlank(message = "Марка обязательна")
    private String mark;

    @NotBlank(message = "Цвет обязателен")
    private String color;

    @NotNull(message = "Год выпуска обязателен")
    @Positive(message = "Год выпуска должен быть положительным числом")
    private Integer yearOfRelease;

    @NotBlank(message = "Номер регистрации обязателен")
    private String stateNumber;

    @PositiveOrZero(message = "Пробег обязателен")
    private int mileage;

    private String notes;


    public CarCreateDto() {
    }

    public CarCreateDto(String model, String mark, String color, Integer yearOfRelease, String stateNumber, int mileage, String notes) {
        this.model = model;
        this.mark = mark;
        this.color = color;
        this.yearOfRelease = yearOfRelease;
        this.stateNumber = stateNumber;
        this.mileage = mileage;
        this.notes = notes;
    }

}