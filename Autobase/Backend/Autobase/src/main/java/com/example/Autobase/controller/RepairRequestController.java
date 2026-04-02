package com.example.Autobase.controller;

import com.example.Autobase.dto.request.repairRequest.RepairRequestCreateDto;
import com.example.Autobase.dto.request.repairRequest.RepairRequestUpdateDto;
import com.example.Autobase.dto.response.repairRequest.RepairRequestResponseDto;
import com.example.Autobase.exception.DriverNotFoundException;
import com.example.Autobase.model.enums.RepairRequestStatus;
import com.example.Autobase.security.CustomUserDetails;
import com.example.Autobase.service.DriverService;
import com.example.Autobase.service.RepairRequestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repair-requests")
public class RepairRequestController {

    private final RepairRequestService repairRequestService;
    private final DriverService driverService;

    public RepairRequestController(RepairRequestService repairRequestService, DriverService driverService) {
        this.repairRequestService = repairRequestService;
        this.driverService = driverService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    public ResponseEntity<List<RepairRequestResponseDto>> getAllRepairRequests() {
        return ResponseEntity.status(HttpStatus.OK).body(repairRequestService.getAllRepairRequests());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER') or @repairRequestController.isDriverOwner(#id)")
    public ResponseEntity<RepairRequestResponseDto> getRepairRequestById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(repairRequestService.getRepairRequestById(id));
    }

    @GetMapping("/driver/{driverId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER') or @repairRequestController.isDriverOwnerByDriverId(#driverId)")
    public ResponseEntity<List<RepairRequestResponseDto>> getRepairRequestsByDriverId(
            @PathVariable("driverId") Long driverId) {
        return ResponseEntity.status(HttpStatus.OK).body(repairRequestService.getRepairRequestsByDriverId(driverId));
    }

    @GetMapping("/car/{carId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    public ResponseEntity<List<RepairRequestResponseDto>> getRepairRequestsByCarId(@PathVariable("carId") Long carId) {
        return ResponseEntity.status(HttpStatus.OK).body(repairRequestService.getRepairRequestsByCarId(carId));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    public ResponseEntity<List<RepairRequestResponseDto>> getRepairRequestsByStatus(
            @PathVariable("status") String status) {
        return ResponseEntity.status(HttpStatus.OK).body(repairRequestService.getRepairRequestsByStatus(status));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('DRIVER', 'DISPATCHER')")
    public ResponseEntity<RepairRequestResponseDto> createRepairRequest(
            @Valid @RequestBody RepairRequestCreateDto createDto) {
        RepairRequestResponseDto created = repairRequestService.createRepairRequest(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RepairRequestResponseDto> updateRepairRequest(@PathVariable("id") Long id,
            @Valid @RequestBody RepairRequestUpdateDto updateDto) {
        RepairRequestResponseDto updated = repairRequestService.updateRepairRequest(id, updateDto);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    public ResponseEntity<Void> updateRepairRequestStatus(@PathVariable("id") Long id,
            @RequestParam RepairRequestStatus repairRequestStatus) {
        repairRequestService.updateRepairRequestStatus(id, repairRequestStatus);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRepairRequest(@PathVariable("id") Long id) {
        repairRequestService.deleteRepairRequest(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public boolean isDriverOwner(Long requestId) {
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
        RepairRequestResponseDto request = repairRequestService.getRepairRequestById(requestId);
        return request.getDriverId() != null && request.getDriverId().equals(currentDriverId);
    }

    public boolean isDriverOwnerByDriverId(Long driverId) {
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