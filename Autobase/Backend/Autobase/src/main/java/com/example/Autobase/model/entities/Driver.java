package com.example.Autobase.model.entities;

import com.example.Autobase.model.enums.DriverStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Driver {
    private Long id;
    private Long userId;
    private String name;
    private String phone;
    private DriverStatus status;
    private Integer experienceYears;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Driver() {
        this.status = DriverStatus.active;
        this.experienceYears = 0;
    }

}
