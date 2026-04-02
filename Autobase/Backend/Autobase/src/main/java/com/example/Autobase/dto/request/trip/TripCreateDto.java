package com.example.Autobase.dto.request.trip;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TripCreateDto {

    @NotBlank(message = "начало маршрута обязательно")
    private String origin;

    @NotBlank(message = "конец маршрута обязателен")
    private String destination;


}
