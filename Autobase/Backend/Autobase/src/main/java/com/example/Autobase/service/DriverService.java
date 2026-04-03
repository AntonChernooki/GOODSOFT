package com.example.Autobase.service;

import com.example.Autobase.dao.DriverDao;
import com.example.Autobase.dto.request.driver.DriverCreateDto;
import com.example.Autobase.dto.request.driver.DriverUpdateDto;
import com.example.Autobase.dto.response.driver.DriverResponseDto;
import com.example.Autobase.exception.DriverNotFoundException;
import com.example.Autobase.model.entities.Driver;
import com.example.Autobase.model.enums.DriverStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DriverService {
    private final DriverDao driverDao;

    public DriverService(DriverDao driverDao) {
        this.driverDao = driverDao;
    }

    public Long getDriverIdByUserId(Long userId) {
        Driver driver = driverDao.getDriverByUserId(userId)
                .orElseThrow(() -> new DriverNotFoundException("Водитель не найден по userId = " + userId));
        return driver.getId();
    }

    public List<DriverResponseDto> getAllDrivers() {
        return driverDao.getAllDrivers().stream()
                .map(this::driverToResponseDto)
                .toList();
    }

    public DriverResponseDto getDriverById(Long driverId) {
        Driver driver = driverDao.getDriverById(driverId)
                .orElseThrow(() -> new DriverNotFoundException("Водитель не найден по driverId = " + driverId));
        return driverToResponseDto(driver);
    }

    public DriverResponseDto getDriverByUserId(Long userId) {
        Driver driver = driverDao.getDriverByUserId(userId)
                .orElseThrow(() -> new DriverNotFoundException("Водитель не найден по userId = " + userId));
        return driverToResponseDto(driver);
    }

    public List<DriverResponseDto> getActiveDrivers() {
        return driverDao.getActiveDrivers().stream()
                .map(this::driverToResponseDto)
                .toList();
    }

    public DriverResponseDto addDriver(DriverCreateDto createDto) {
        Driver driver = new Driver();
        driver.setUserId(createDto.getUserId());
        driver.setName(createDto.getName());
        driver.setPhone(createDto.getPhone());
        driver.setExperienceYears(createDto.getExperienceYears());
        driver.setNotes(createDto.getNotes());
        driverDao.addDriver(driver);
        Driver savedDriver = driverDao.getDriverById(driver.getId())
                .orElseThrow(() -> new DriverNotFoundException("Водитель не найден после сохранения"));
        return driverToResponseDto(savedDriver);
    }

    public DriverResponseDto updateDriver(Long driverId, DriverUpdateDto updateDto) {
        Driver driver = driverDao.getDriverById(driverId)
                .orElseThrow(() -> new DriverNotFoundException("Водитель не найден по id = " + driverId));

        if (updateDto.getName() != null) {
            driver.setName(updateDto.getName());
        }
        if (updateDto.getPhone() != null) {
            driver.setPhone(updateDto.getPhone());
        }
        if (updateDto.getExperienceYears() != null) {
            driver.setExperienceYears(updateDto.getExperienceYears());
        }
        if (updateDto.getNotes() != null) {
            driver.setNotes(updateDto.getNotes());
        }
        if (updateDto.getStatus() != null) {
            driver.setStatus(DriverStatus.valueOf(updateDto.getStatus()));
        }

        driverDao.updateDriver(driver);
        Driver updatedDriver = driverDao.getDriverById(driverId).orElseThrow(() -> new DriverNotFoundException("Водитель не найден после обновления"));
        return driverToResponseDto(updatedDriver);
    }

    public void updateDriverStatus(Long driverId, String status) {
        Driver driver = driverDao.getDriverById(driverId).orElseThrow(() -> new DriverNotFoundException("Водитель не найден по id = " + driverId));
        driverDao.updateDriverStatus(driverId, status);
    }

    public void deleteDriver(Long driverId) {
        Driver driver = driverDao.getDriverById(driverId)
                .orElseThrow(() -> new DriverNotFoundException("Водитель не найден по id = " + driverId));
        driverDao.deleteDriver(driverId);
    }

    private DriverResponseDto driverToResponseDto(Driver driver) {
        return new DriverResponseDto(
                driver.getId(),
                driver.getUserId(),
                driver.getName(),
                driver.getPhone(),
                driver.getStatus().name(),
                driver.getExperienceYears(),
                driver.getNotes(),
                driver.getCreatedAt(),
                driver.getUpdatedAt()
        );
    }
}