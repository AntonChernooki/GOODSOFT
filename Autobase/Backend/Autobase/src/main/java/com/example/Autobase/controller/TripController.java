package com.example.Autobase.controller;

import com.example.Autobase.dto.request.trip.TripAssignDto;
import com.example.Autobase.dto.request.trip.TripCreateDto;
import com.example.Autobase.dto.request.trip.TripUpdateDto;
import com.example.Autobase.dto.response.trip.TripResponseDto;
import com.example.Autobase.exception.DriverNotFoundException;
import com.example.Autobase.model.enums.TripStatus;
import com.example.Autobase.security.CustomUserDetails;
import com.example.Autobase.service.DriverService;
import com.example.Autobase.service.TripService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;
    private final DriverService driverService;

    public TripController(TripService tripService, DriverService driverService) {
        this.tripService = tripService;
        this.driverService = driverService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    public ResponseEntity<List<TripResponseDto>> getAllTrips() {
        return ResponseEntity.ok(tripService.getAllTrips());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER') or @tripController.isDriver(#id)")
    public ResponseEntity<TripResponseDto> getTripById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(tripService.getTripById(id));
    }

    @GetMapping("/driver/{driverId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER') or @tripController.isCurrentDriver(#driverId)")
    public ResponseEntity<List<TripResponseDto>> getTripsByDriverId(@PathVariable("driverId") Long driverId) {
        return ResponseEntity.ok(tripService.getTripsByDriverId(driverId));
    }

    @GetMapping("/car/{carId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    public ResponseEntity<List<TripResponseDto>> getTripsByCarId(@PathVariable("carId") Long carId) {
        return ResponseEntity.ok(tripService.getTripsByCarId(carId));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    public ResponseEntity<List<TripResponseDto>> getTripsByStatus(@PathVariable("status") String status) {
        TripStatus tripStatus = TripStatus.valueOf(status.toUpperCase());
        return ResponseEntity.ok(tripService.getTripsByStatus(tripStatus));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    public ResponseEntity<TripResponseDto> createTrip(@Valid @RequestBody TripCreateDto createDto) {
        TripResponseDto created = tripService.addTrip(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{tripId}/assign")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    public ResponseEntity<Void> assignDriverAndCar(@PathVariable("tripId") Long tripId,
            @Valid @RequestBody TripAssignDto assignDto) {
        tripService.assignDriverAndCar(tripId, assignDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{tripId}/start")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER') or @tripController.isDriver(#tripId)")
    public ResponseEntity<Void> startTrip(@PathVariable("tripId") Long tripId) {
        tripService.startTrip(tripId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{tripId}/complete")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER') or @tripController.isDriver(#tripId)")
    public ResponseEntity<Void> completeTrip(@PathVariable("tripId") Long tripId) {
        tripService.completeTrip(tripId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{tripId}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER') or @tripController.isDriver(#tripId)")
    public ResponseEntity<Void> cancelTrip(@PathVariable("tripId") Long tripId) {
        tripService.cancelTrip(tripId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{tripId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    public ResponseEntity<Void> updateTrip(@PathVariable("tripId") Long tripId,
            @Valid @RequestBody TripUpdateDto updateDto) {
        tripService.updateTrip(tripId, updateDto);
        return ResponseEntity.noContent().build();
    }

    public boolean isDriver(@PathVariable("tripId") Long tripId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal() instanceof String) {
            return false;
        }
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        Long currentUserId = userDetails.getId();
        Long currentDriverId;
        try {
            currentDriverId = driverService.getDriverIdByUserId(currentUserId);
        } catch (DriverNotFoundException e) {
            return false;
        }
        if (currentDriverId == null) {
            return false;
        }
        TripResponseDto trip = tripService.getTripById(tripId);
        return trip.getDriverId() != null && trip.getDriverId().equals(currentDriverId);
    }

    public boolean isCurrentDriver(@PathVariable("driverId") Long driverId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal() instanceof String) {
            return false;
        }
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        Long currentUserId = userDetails.getId();
        Long currentDriverId;
        try {
            currentDriverId = driverService.getDriverIdByUserId(currentUserId);
        } catch (DriverNotFoundException e) {
            return false;
        }
        return currentDriverId != null && currentDriverId.equals(driverId);
    }
}