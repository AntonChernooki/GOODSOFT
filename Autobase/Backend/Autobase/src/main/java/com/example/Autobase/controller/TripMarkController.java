package com.example.Autobase.controller;

import com.example.Autobase.dto.request.tripMark.TripMarkRequestDto;
import com.example.Autobase.dto.response.tripMark.TripMarkResponseDto;
import com.example.Autobase.exception.DriverNotFoundException;
import com.example.Autobase.security.CustomUserDetails;
import com.example.Autobase.service.DriverService;
import com.example.Autobase.service.TripMarkService;
import com.example.Autobase.service.TripService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trip-marks")
public class TripMarkController {

    private final TripMarkService tripMarkService;
    private final DriverService driverService;
    private final TripService tripService;

    public TripMarkController(TripMarkService tripMarkService,
            DriverService driverService,
            TripService tripService) {
        this.tripMarkService = tripMarkService;
        this.driverService = driverService;
        this.tripService = tripService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER') or @tripMarkController.isDriverOfTripMark(#id)")
    public ResponseEntity<TripMarkResponseDto> getTripMarkById(@PathVariable("id") Long id) {
        TripMarkResponseDto mark = tripMarkService.getTripMarkById(id);
        return ResponseEntity.ok(mark);
    }

    @GetMapping("/trip/{tripId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER') or @tripMarkController.isDriverOfTrip(#tripId)")
    public ResponseEntity<TripMarkResponseDto> getTripMarkByTripId(@PathVariable("tripId") Long tripId) {
        TripMarkResponseDto mark = tripMarkService.getTripMarkByTripId(tripId);
        return ResponseEntity.ok(mark);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DRIVER')")
    public ResponseEntity<TripMarkResponseDto> createOrUpdateTripMark(@Valid @RequestBody TripMarkRequestDto dto) {
        TripMarkResponseDto saved = tripMarkService.saveOrUpdateTripMark(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DRIVER')")
    public ResponseEntity<Void> deleteTripMarkById(@PathVariable("id") Long id) {
        tripMarkService.deleteTripMarkById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/trip/{tripId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DRIVER')")
    public ResponseEntity<Void> deleteTripMarkByTripId(@PathVariable("tripId") Long tripId) {
        tripMarkService.deleteTripMarkByTripId(tripId);
        return ResponseEntity.noContent().build();
    }

    public boolean isDriverOfTrip(Long tripId) {
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
        var trip = tripService.getTripById(tripId);
        return trip.getDriverId() != null && trip.getDriverId().equals(currentDriverId);
    }

    public boolean isDriverOfTripMark(Long markId) {
        TripMarkResponseDto mark = tripMarkService.getTripMarkById(markId);
        return isDriverOfTrip(mark.getTripId());
    }
}