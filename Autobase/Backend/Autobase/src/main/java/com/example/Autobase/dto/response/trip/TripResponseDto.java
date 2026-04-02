package com.example.Autobase.dto.response.trip;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TripResponseDto {
    private Long id;
    private String origin;
    private String destination;
    private String status;
    private Long driverId;
    private Long carId;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
