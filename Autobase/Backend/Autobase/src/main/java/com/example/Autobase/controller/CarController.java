package com.example.Autobase.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.Autobase.dto.request.car.CarCreateDto;
import com.example.Autobase.dto.request.car.CarUpdateDto;
import com.example.Autobase.dto.response.car.CarResponseDto;
import com.example.Autobase.service.CarService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    public ResponseEntity<List<CarResponseDto>> getAllCars() {
        return ResponseEntity.status(HttpStatus.OK).body(carService.getAllCars());
    }

    @GetMapping("/{id}")
    // @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    public ResponseEntity<CarResponseDto> getCarById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(carService.getCarById(id));
    }

    @GetMapping("/state-number/{stateNumber}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    public ResponseEntity<CarResponseDto> getCarByStateNumber(@PathVariable("stateNumber") String stateNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(carService.getCarByStateNumber(stateNumber));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    public ResponseEntity<List<CarResponseDto>> getCarsByStatus(@PathVariable("status") String status) {
        return ResponseEntity.status(HttpStatus.OK).body(carService.getCarsByStatus(status));
    }

    @GetMapping("/mark/{mark}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    public ResponseEntity<List<CarResponseDto>> getCarsByMark(@PathVariable("mark") String mark) {
        return ResponseEntity.status(HttpStatus.OK).body(carService.getCarsByMark(mark));
    }

    @GetMapping("/color/{color}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    public ResponseEntity<List<CarResponseDto>> getCarsByColor(@PathVariable("color") String color) {
        return ResponseEntity.status(HttpStatus.OK).body(carService.getCarsByColor(color));
    }

    @GetMapping("/year/{year}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    public ResponseEntity<List<CarResponseDto>> getCarsByYearOfRelease(@PathVariable("year") Integer year) {
        return ResponseEntity.status(HttpStatus.OK).body(carService.getCarsByYearOfRelease(year));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarResponseDto> createCar(@Valid @RequestBody CarCreateDto createDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.addCar(createDto));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarResponseDto> updateCar(@PathVariable("id") Long id,
            @Valid @RequestBody CarUpdateDto updateDto) {
        return ResponseEntity.status(HttpStatus.OK).body(carService.updateCar(id, updateDto));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateCarStatus(@PathVariable("id") Long id,
            @RequestParam String status) {
        carService.updateCarStatus(id, status);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCar(@PathVariable("id") Long id) {
        carService.deleteCar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}