package com.example.Autobase.service;

import com.example.Autobase.dao.TripDao;
import com.example.Autobase.dto.request.trip.TripAssignDto;
import com.example.Autobase.dto.request.trip.TripCreateDto;
import com.example.Autobase.dto.request.trip.TripUpdateDto;
import com.example.Autobase.dto.response.trip.TripResponseDto;
import com.example.Autobase.exception.TripNotFoundException;
import com.example.Autobase.exception.TripOperationNotAllowedException;
import com.example.Autobase.model.entities.Trip;
import com.example.Autobase.model.enums.TripStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TripService {
    private final TripDao tripDao;

    public TripService(TripDao tripDao) {
        this.tripDao = tripDao;
    }

    public TripResponseDto getTripById(Long id) {
        Trip trip = tripDao.getTripById(id).orElseThrow(() -> new TripNotFoundException("заявка на рейс не нашлась по id = " + id));
        return tripToResponseDto(trip);
    }

    public List<TripResponseDto> getAllTrips() {
        List<Trip> tripList = tripDao.getAllTrip();
        return tripList.stream().map(this::tripToResponseDto).toList();
    }

    public List<TripResponseDto> getTripsByDriverId(Long id) {
        List<Trip> tripList = tripDao.getTripByDriverId(id);
        return tripList.stream().map(this::tripToResponseDto).toList();
    }

    public List<TripResponseDto> getTripsByCarId(Long id) {
        List<Trip> tripList = tripDao.getTripByCarId(id);
        return tripList.stream().map(this::tripToResponseDto).toList();
    }

    public List<TripResponseDto> getTripsByStatus(TripStatus status) {
        List<Trip> tripList = tripDao.getTripByStatus(status.name());
        return tripList.stream().map(this::tripToResponseDto).toList();
    }

    public TripResponseDto addTrip(TripCreateDto tripCreateDto) {
        Trip trip = new Trip();
        trip.setOrigin(tripCreateDto.getOrigin());
        trip.setDestination(tripCreateDto.getDestination());
        tripDao.addTrip(trip);
        Trip savedTrip = tripDao.getTripById(trip.getId()).orElseThrow(() -> new TripNotFoundException("Рейс не найден после сохранения"));
        return tripToResponseDto(savedTrip);
    }


    public void assignDriverAndCar(Long tripId, TripAssignDto dto) {
        Trip trip = tripDao.getTripById(tripId).orElseThrow(() -> new TripNotFoundException("заявка на рейс не нашлась по id = " + tripId));
        if (trip.getStatus() != TripStatus.await) {
            throw new TripOperationNotAllowedException("Назначить водителя и автомобиль можно только рейсу в статусе 'await'");
        }
        tripDao.assignDriverAndCar(tripId, dto.getDriverId(), dto.getCarId());
    }

    public void startTrip(Long tripId) {
        Trip trip = tripDao.getTripById(tripId).orElseThrow(() -> new TripNotFoundException("заявка на рейс не нашлась по id = " + tripId));
        if (trip.getStatus() != TripStatus.await) {
            throw new TripOperationNotAllowedException("Начать можно только рейс в статусе 'await'");
        }
        if (trip.getDriverId() == null || trip.getCarId() == null) {
            throw new TripOperationNotAllowedException("Для начала рейса должны быть назначены водитель и автомобиль");
        }
        tripDao.startTrip(tripId, LocalDateTime.now());
    }


    public void completeTrip(Long tripId) {
        Trip trip = tripDao.getTripById(tripId).orElseThrow(() -> new TripNotFoundException("заявка на рейс не нашлась по id = " + tripId));
        if (trip.getStatus() != TripStatus.in_progress) {
            throw new TripOperationNotAllowedException("Завершить можно только рейс в статусе 'in_progress'");
        }
        if (trip.getStartedAt() == null) {
            throw new TripOperationNotAllowedException("Нельзя завершить рейс, который не был начат");
        }
        tripDao.completeTrip(tripId, LocalDateTime.now());
    }


    public void cancelTrip(Long tripId) {
        Trip trip = tripDao.getTripById(tripId).orElseThrow(() -> new TripNotFoundException("заявка на рейс не нашлась по id = " + tripId));
        if (trip.getStatus() == TripStatus.completed) {
            throw new TripOperationNotAllowedException("Нельзя отменить уже завершённый рейс");
        }
        tripDao.cancelTrip(tripId);
    }

    public void updateTrip(Long tripId, TripUpdateDto updateDto) {
        Trip trip = tripDao.getTripById(tripId).orElseThrow(() -> new TripNotFoundException("заявка на рейс не нашлась по id = " + tripId));

        if (trip.getStatus() != TripStatus.await) {
            throw new TripOperationNotAllowedException(
                    "Изменить рейс можно только в статусе 'await'");
        }

        if (updateDto.getOrigin() == null && updateDto.getDestination() == null &&
                updateDto.getDriverId() == null && updateDto.getCarId() == null) {
            throw new IllegalArgumentException("Нет данных для обновления");
        }

        if (updateDto.getOrigin() != null) {
            trip.setOrigin(updateDto.getOrigin());
        }
        if (updateDto.getDestination() != null) {
            trip.setDestination(updateDto.getDestination());
        }
        if (updateDto.getDriverId() != null) {
            trip.setDriverId(updateDto.getDriverId());
        }
        if (updateDto.getCarId() != null) {
            trip.setCarId(updateDto.getCarId());
        }

        tripDao.updateTrip(trip);
    }


    private TripResponseDto tripToResponseDto(Trip trip) {
        TripResponseDto dto = new TripResponseDto();
        dto.setId(trip.getId());
        dto.setOrigin(trip.getOrigin());
        dto.setDestination(trip.getDestination());
        dto.setStatus(trip.getStatus().name());
        dto.setStartedAt(trip.getStartedAt());
        dto.setCompletedAt(trip.getCompletedAt());
        dto.setCreatedAt(trip.getCreatedAt());
        dto.setUpdatedAt(trip.getUpdatedAt());
        if (trip.getCarId() != null) {
            dto.setCarId(trip.getCarId());
        }
        if (trip.getDriverId() != null) {
            dto.setDriverId(trip.getDriverId());
        }

        return dto;
    }
}
