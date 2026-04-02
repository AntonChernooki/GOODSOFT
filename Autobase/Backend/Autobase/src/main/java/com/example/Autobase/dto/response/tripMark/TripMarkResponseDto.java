package com.example.Autobase.dto.response.tripMark;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TripMarkResponseDto {
    private Long id;
    private Long tripId;
    private BigDecimal fuelConsumed;
    private String conditionNotes;
    private LocalDateTime markDate;
}