package com.example.Autobase.dto.request.repairRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RepairRequestCreateDto {
    @NotNull(message = "DriverId обязателен")
    private Long driverId;

    @NotNull(message = "CarId обязателен")
    private Long carId;

    @NotBlank(message = "описание обязательно")
    private String description;
}
