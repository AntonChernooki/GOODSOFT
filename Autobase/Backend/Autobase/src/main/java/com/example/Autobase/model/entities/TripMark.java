package com.example.Autobase.model.entities;



import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TripMark {
    private Long id;
    private Long tripId;
    private BigDecimal fuelConsumed;
    private String conditionNotes;
    private LocalDateTime markDate;

    public TripMark() {
    }

}
