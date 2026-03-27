package com.example.Autobase.model.entities;


import com.example.Autobase.model.enums.CarStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Car {
    private Long id;
    private String model;
    private String mark;
    private String color;
    private Integer yearOfRelease;
    private String stateNumber;
    private CarStatus status;
    private Integer mileage;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Car() {
        this.status = CarStatus.available;
        this.mileage = 0;
    }

}