package com.example.Autobase.model.entities;



import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TripMark {
    private Long id;
    private Trip trip;
    private BigDecimal fuelConsumed;
    private String conditionNotes;
    private LocalDateTime markDate;

    public TripMark() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public BigDecimal getFuelConsumed() {
        return fuelConsumed;
    }

    public void setFuelConsumed(BigDecimal fuelConsumed) {
        this.fuelConsumed = fuelConsumed;
    }

    public String getConditionNotes() {
        return conditionNotes;
    }

    public void setConditionNotes(String conditionNotes) {
        this.conditionNotes = conditionNotes;
    }

    public LocalDateTime getMarkDate() {
        return markDate;
    }

    public void setMarkDate(LocalDateTime markDate) {
        this.markDate = markDate;
    }
}
