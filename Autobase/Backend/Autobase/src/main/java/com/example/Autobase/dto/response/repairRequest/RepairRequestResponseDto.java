package com.example.Autobase.dto.response.repairRequest;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class RepairRequestResponseDto {

    private Long id;
    private Long driverId;
    private Long carId;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private LocalDateTime updatedAt;
}
