package com.example.Autobase.model.entities;

import com.example.Autobase.model.enums.TripStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Trip {
    private Long id;
    private String origin;
    private String destination;
    private TripStatus status;
    private Long driverId;
    private Long carId;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Trip() {
        this.status = TripStatus.await;
    }
}
