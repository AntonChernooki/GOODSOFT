package com.example.Autobase.service;

import com.example.Autobase.dao.CarDao;
import com.example.Autobase.dto.request.car.CarCreateDto;
import com.example.Autobase.dto.request.car.CarUpdateDto;
import com.example.Autobase.dto.response.car.CarResponseDto;
import com.example.Autobase.exception.CarNotFoundException;
import com.example.Autobase.exception.DuplicateStateNumberException;
import com.example.Autobase.model.entities.Car;
import com.example.Autobase.model.enums.CarStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CarService {

    private final CarDao carDao;

    public CarService(CarDao carDao) {
        this.carDao = carDao;
    }

    public List<CarResponseDto> getAllCars() {
        return carDao.getAllCar().stream()
                .map(this::toResponseDto)
                .toList();
    }

    public CarResponseDto getCarById(Long id) {
        Car car = carDao.getCarById(id)
                .orElseThrow(() -> new CarNotFoundException("Автомобиль не найден по id = " + id));
        return toResponseDto(car);
    }

    public CarResponseDto getCarByStateNumber(String stateNumber) {
        Car car = carDao.getCarByStateNumber(stateNumber)
                .orElseThrow(() -> new CarNotFoundException("Автомобиль не найден по госномеру = " + stateNumber));
        return toResponseDto(car);
    }

    public List<CarResponseDto> getCarsByStatus(String status) {
        return carDao.getCarByStatus(status).stream()
                .map(this::toResponseDto)
                .toList();
    }

    public List<CarResponseDto> getCarsByMark(String mark) {
        return carDao.getCarByMark(mark).stream()
                .map(this::toResponseDto)
                .toList();
    }

    public List<CarResponseDto> getCarsByColor(String color) {
        return carDao.getCarByColor(color).stream()
                .map(this::toResponseDto)
                .toList();
    }

    public List<CarResponseDto> getCarsByYearOfRelease(Integer year) {
        return carDao.getCarByYearOfRelease(year).stream()
                .map(this::toResponseDto)
                .toList();
    }

    public CarResponseDto addCar(CarCreateDto createDto) {
        Optional<Car> existing = carDao.getCarByStateNumber(createDto.getStateNumber());
        if (existing.isPresent()) {
            throw new DuplicateStateNumberException(
                    "Автомобиль с госномером " + createDto.getStateNumber() + " уже существует");
        }

        Car car = new Car();
        car.setModel(createDto.getModel());
        car.setMark(createDto.getMark());
        car.setColor(createDto.getColor());
        car.setYearOfRelease(createDto.getYearOfRelease());
        car.setStateNumber(createDto.getStateNumber());
        car.setMileage(createDto.getMileage());
        car.setNotes(createDto.getNotes());

        carDao.addCar(car);
        Car saved = carDao.getCarById(car.getId())
                .orElseThrow(() -> new CarNotFoundException("Автомобиль не найден после сохранения"));
        return toResponseDto(saved);
    }

    public CarResponseDto updateCar(Long id, CarUpdateDto updateDto) {
        Car car = carDao.getCarById(id)
                .orElseThrow(() -> new CarNotFoundException("Автомобиль не найден по id = " + id));

        if (updateDto.getStateNumber() != null && !updateDto.getStateNumber().equals(car.getStateNumber())) {
            Optional<Car> existing = carDao.getCarByStateNumber(updateDto.getStateNumber());
            if (existing.isPresent() && !existing.get().getId().equals(id)) {
                throw new DuplicateStateNumberException(
                        "Госномер " + updateDto.getStateNumber() + " уже используется другим автомобилем");
            }
            car.setStateNumber(updateDto.getStateNumber());
        }

        if (updateDto.getModel() != null)
            car.setModel(updateDto.getModel());
        if (updateDto.getMark() != null)
            car.setMark(updateDto.getMark());
        if (updateDto.getColor() != null)
            car.setColor(updateDto.getColor());
        if (updateDto.getYearOfRelease() != null)
            car.setYearOfRelease(updateDto.getYearOfRelease());
        if (updateDto.getMileage() != null)
            car.setMileage(updateDto.getMileage());
        if (updateDto.getNotes() != null)
            car.setNotes(updateDto.getNotes());
        if (updateDto.getStatus() != null)
            car.setStatus(CarStatus.valueOf(updateDto.getStatus()));

        carDao.updateCar(car);
        Car updated = carDao.getCarById(id)
                .orElseThrow(() -> new CarNotFoundException("Автомобиль не найден после обновления"));
        return toResponseDto(updated);
    }

    public void updateCarStatus(Long id, String status) {
        carDao.getCarById(id)
                .orElseThrow(() -> new CarNotFoundException("Автомобиль не найден по id = " + id));
        carDao.updateCarStatus(id, status);
    }

    public void deleteCar(Long id) {
        carDao.getCarById(id)
                .orElseThrow(() -> new CarNotFoundException("Автомобиль не найден по id = " + id));
        carDao.deleteCar(id);
    }

    private CarResponseDto toResponseDto(Car car) {
        return new CarResponseDto(
                car.getId(),
                car.getModel(),
                car.getMark(),
                car.getColor(),
                car.getYearOfRelease(),
                car.getStateNumber(),
                car.getStatus().name(),
                car.getMileage(),
                car.getNotes(),
                car.getCreatedAt(),
                car.getUpdatedAt());
    }
}