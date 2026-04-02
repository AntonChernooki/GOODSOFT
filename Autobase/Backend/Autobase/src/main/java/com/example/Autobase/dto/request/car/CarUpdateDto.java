package com.example.Autobase.dto.request.car;


import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CarUpdateDto {
    private String model;
    private String mark;
    private String color;
    @Positive(message = "Год выпуска должен быть положительным числом")
    private Integer yearOfRelease;
    private String stateNumber;
    @PositiveOrZero(message = "Пробег не может быть отрицательным")
    private Integer mileage;
    private String notes;
    private String status;
}
