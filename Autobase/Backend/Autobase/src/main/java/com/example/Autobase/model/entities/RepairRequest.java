package com.example.Autobase.model.entities;


import com.example.Autobase.model.enums.RepairRequestStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RepairRequest {
    private Long id;
    private Driver driver;
    private Car car;
    private String description;
    private RepairRequestStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private LocalDateTime updatedAt;

    public RepairRequest() {
        this.status = RepairRequestStatus.submitted;
    }

}
