package com.example.Autobase.dto.response.car;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CarResponseDto {

    private Long id;
    private String model;
    private String mark;
    private String color;
    private Integer yearOfRelease;
    private String stateNumber;
    private String status;
    private int mileage;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CarResponseDto() {
    }

    public CarResponseDto(Long id, String model, String mark, String color, Integer yearOfRelease, String stateNumber, String status, int mileage, String notes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.model = model;
        this.mark = mark;
        this.color = color;
        this.yearOfRelease = yearOfRelease;
        this.stateNumber = stateNumber;
        this.status = status;
        this.mileage = mileage;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
