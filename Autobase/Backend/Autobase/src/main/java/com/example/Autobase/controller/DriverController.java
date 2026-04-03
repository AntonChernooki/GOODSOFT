package com.example.Autobase.controller;

import com.example.Autobase.dto.request.driver.DriverCreateDto;
import com.example.Autobase.dto.request.driver.DriverStatusUpdateDto;
import com.example.Autobase.dto.request.driver.DriverUpdateDto;
import com.example.Autobase.dto.response.driver.DriverResponseDto;
import com.example.Autobase.security.CustomUserDetails;
import com.example.Autobase.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    public ResponseEntity<List<DriverResponseDto>> getAllDrivers() {
        List<DriverResponseDto> drivers = driverService.getAllDrivers();
        return ResponseEntity.status(HttpStatus.OK).body(drivers);
    }

    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    public ResponseEntity<List<DriverResponseDto>> getActiveDrivers() {
        List<DriverResponseDto> drivers = driverService.getActiveDrivers();
        return ResponseEntity.status(HttpStatus.OK).body(drivers);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER') or @driverController.isDriverOwner(#id)")
    public ResponseEntity<DriverResponseDto> getDriverById(@PathVariable("id") Long id) {
        DriverResponseDto driver = driverService.getDriverById(id);
        return ResponseEntity.status(HttpStatus.OK).body(driver);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER') or #userId == authentication.principal.id")
    public ResponseEntity<DriverResponseDto> getDriverByUserId(@PathVariable("userId") Long userId) {
        DriverResponseDto driver = driverService.getDriverByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(driver);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','DRIVER')")
    public ResponseEntity<DriverResponseDto> createDriver(@Valid @RequestBody DriverCreateDto createDto) {
        DriverResponseDto created = driverService.addDriver(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DriverResponseDto> updateDriver(@PathVariable("id") Long id,
            @Valid @RequestBody DriverUpdateDto updateDto) {
        DriverResponseDto updated = driverService.updateDriver(id, updateDto);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    public ResponseEntity<Void> updateDriverStatus(@PathVariable("id") Long id,
            @Valid @RequestBody DriverStatusUpdateDto statusDto) {
        driverService.updateDriverStatus(id, statusDto.getStatus());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteDriver(@PathVariable("id") Long id) {
        driverService.deleteDriver(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public boolean isDriverOwner(Long driverId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal() instanceof String) {
            return false;
        }
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        Long currentUserId = userDetails.getId();
        DriverResponseDto driver = driverService.getDriverById(driverId);
        return driver.getUserId().equals(currentUserId);
    }
}