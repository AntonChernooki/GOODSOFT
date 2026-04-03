package com.example.Autobase.dto.request.tripMark;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class TripMarkRequestDto {
    @NotNull(message = "TripId обязателен")
    private Long tripId;

    @PositiveOrZero(message = "количество топлива обязательно")
    @DecimalMax(value = "99999999.99", message = "Количество топлива не должно превышать 99 999 999.99")
    private BigDecimal fuelConsumed;

    private String conditionNotes;
}