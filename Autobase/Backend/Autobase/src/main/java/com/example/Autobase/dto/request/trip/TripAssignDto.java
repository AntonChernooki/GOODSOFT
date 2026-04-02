package com.example.Autobase.dto.request.trip;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TripAssignDto {

    @NotNull(message = "DriveId обязателен")
    private Long driverId;

    @NotNull(message = "CarId обязателен")
    private Long carId;

}
