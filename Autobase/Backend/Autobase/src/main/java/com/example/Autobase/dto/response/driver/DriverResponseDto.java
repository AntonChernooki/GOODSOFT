package com.example.Autobase.dto.response.driver;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DriverResponseDto {

    private Long id;
    private Long userId;
    private String name;
    private String phone;
    private String status;
    private int experienceYears;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DriverResponseDto() {

    }

    public DriverResponseDto(Long id, Long userId, String name, String phone, String status, int experienceYears, String notes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.status = status;
        this.experienceYears = experienceYears;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
