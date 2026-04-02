package com.example.Autobase.service;

import com.example.Autobase.dao.CarDao;
import com.example.Autobase.dao.DriverDao;
import com.example.Autobase.dao.RepairRequestDao;
import com.example.Autobase.dto.request.repairRequest.RepairRequestCreateDto;
import com.example.Autobase.dto.request.repairRequest.RepairRequestUpdateDto;
import com.example.Autobase.dto.response.repairRequest.RepairRequestResponseDto;
import com.example.Autobase.exception.CarNotFoundException;
import com.example.Autobase.exception.DriverNotFoundException;
import com.example.Autobase.exception.RepairRequestNotFoundException;
import com.example.Autobase.exception.RepairRequestOperationNotAllowedException;
import com.example.Autobase.model.entities.RepairRequest;
import com.example.Autobase.model.enums.RepairRequestStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class RepairRequestService {

    private final RepairRequestDao repairRequestDao;
    private final DriverDao driverDao;
    private final CarDao carDao;

    public RepairRequestService(RepairRequestDao repairRequestDao, DriverDao driverDao, CarDao carDao) {
        this.repairRequestDao = repairRequestDao;
        this.driverDao = driverDao;
        this.carDao = carDao;
    }

    public RepairRequestResponseDto getRepairRequestById(Long id) {
        RepairRequest request = repairRequestDao.getRepairRequestById(id)
                .orElseThrow(() -> new RepairRequestNotFoundException("Заявка на ремонт не найдена по id = " + id));
        return toResponseDto(request);
    }

    public List<RepairRequestResponseDto> getAllRepairRequests() {
        return repairRequestDao.getAllRepairRequest().stream()
                .map(this::toResponseDto)
                .toList();
    }

    public List<RepairRequestResponseDto> getRepairRequestsByDriverId(Long driverId) {
        return repairRequestDao.getRepairRequestByDriverId(driverId).stream()
                .map(this::toResponseDto)
                .toList();
    }

    public List<RepairRequestResponseDto> getRepairRequestsByCarId(Long carId) {
        return repairRequestDao.getRepairRequestByCarId(carId).stream()
                .map(this::toResponseDto)
                .toList();
    }

    public List<RepairRequestResponseDto> getRepairRequestsByStatus(String status) {
        return repairRequestDao.getRepairRequestByStatus(status.toUpperCase()).stream()
                .map(this::toResponseDto)
                .toList();
    }

    public RepairRequestResponseDto createRepairRequest(RepairRequestCreateDto createDto) {
        driverDao.getDriverById(createDto.getDriverId())
                .orElseThrow(
                        () -> new DriverNotFoundException("Водитель не найден по id = " + createDto.getDriverId()));
        carDao.getCarById(createDto.getCarId())
                .orElseThrow(() -> new CarNotFoundException("Автомобиль не найден по id = " + createDto.getCarId()));

        RepairRequest request = new RepairRequest();
        request.setDriverId(createDto.getDriverId());
        request.setCarId(createDto.getCarId());
        request.setDescription(createDto.getDescription());
        request.setStatus(RepairRequestStatus.submitted);

        repairRequestDao.addRepairRequest(request);
        RepairRequest saved = repairRequestDao.getRepairRequestById(request.getId())
                .orElseThrow(() -> new RepairRequestNotFoundException("Заявка не найдена после сохранения"));
        return toResponseDto(saved);
    }

    public RepairRequestResponseDto updateRepairRequest(Long id, RepairRequestUpdateDto updateDto) {
        RepairRequest request = repairRequestDao.getRepairRequestById(id)
                .orElseThrow(() -> new RepairRequestNotFoundException("Заявка на ремонт не найдена по id = " + id));

        if (updateDto.getStatus() != null) {
            RepairRequestStatus newStatus = RepairRequestStatus.valueOf(updateDto.getStatus().toUpperCase());
            if (!canChangeStatus(request.getStatus(), newStatus)) {
                throw new RepairRequestOperationNotAllowedException(
                        String.format("Недопустимый переход статуса из %s в %s", request.getStatus(), newStatus));
            }
            request.setStatus(newStatus);
            if (newStatus == RepairRequestStatus.complete && request.getCompletedAt() == null) {
                request.setCompletedAt(LocalDateTime.now());
            }
        }

        if (updateDto.getDescription() != null) {
            request.setDescription(updateDto.getDescription());
        }

        repairRequestDao.updateRepairRequest(request);
        RepairRequest updated = repairRequestDao.getRepairRequestById(id)
                .orElseThrow(() -> new RepairRequestNotFoundException("Заявка не найдена после обновления"));
        return toResponseDto(updated);
    }

    public void updateRepairRequestStatus(Long id, RepairRequestStatus repairRequestStatus) {
        RepairRequest request = repairRequestDao.getRepairRequestById(id)
                .orElseThrow(() -> new RepairRequestNotFoundException("Заявка на ремонт не найдена по id = " + id));

        if (!canChangeStatus(request.getStatus(), repairRequestStatus)) {
            throw new RepairRequestOperationNotAllowedException(
                    String.format("Недопустимый переход статуса из %s в %s", request.getStatus(), repairRequestStatus));
        }
        if (repairRequestStatus == RepairRequestStatus.complete) {
            repairRequestDao.completeRepairRequest(id, LocalDateTime.now());
        } else {
            repairRequestDao.updateRepairRequestStatus(id, repairRequestStatus.name());
        }
    }

    public void deleteRepairRequest(Long id) {
        repairRequestDao.getRepairRequestById(id)
                .orElseThrow(() -> new RepairRequestNotFoundException("Заявка на ремонт не найдена по id = " + id));
        repairRequestDao.deleteRepairRequest(id);
    }

    private boolean canChangeStatus(RepairRequestStatus current, RepairRequestStatus target) {
        return switch (current) {
            case submitted -> target == RepairRequestStatus.in_progress || target == RepairRequestStatus.rejected;
            case in_progress -> target == RepairRequestStatus.complete;
            default -> false;
        };
    }

    private RepairRequestResponseDto toResponseDto(RepairRequest request) {
        RepairRequestResponseDto dto = new RepairRequestResponseDto();
        dto.setId(request.getId());
        dto.setDriverId(request.getDriverId());
        dto.setCarId(request.getCarId());
        dto.setDescription(request.getDescription());
        dto.setStatus(request.getStatus().name());
        dto.setCreatedAt(request.getCreatedAt());
        dto.setCompletedAt(request.getCompletedAt());
        dto.setUpdatedAt(request.getUpdatedAt());
        return dto;
    }
}