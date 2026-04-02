package com.example.Autobase.dto.request.trip;



import lombok.Data;

@Data
public class TripUpdateDto {
    private String origin;
    private String destination;
    private Long driverId;
    private Long carId;
}
