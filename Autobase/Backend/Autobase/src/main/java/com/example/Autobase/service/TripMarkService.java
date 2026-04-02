package com.example.Autobase.service;

import com.example.Autobase.dao.TripMarkDao;
import com.example.Autobase.dto.request.tripMark.TripMarkRequestDto;
import com.example.Autobase.dto.response.tripMark.TripMarkResponseDto;
import com.example.Autobase.exception.TripMarkNotFoundException;
import com.example.Autobase.exception.TripNotFoundException;
import com.example.Autobase.model.entities.TripMark;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class TripMarkService {

    private final TripMarkDao tripMarkDao;
    private final TripService tripService;  // для проверки существования поездки

    public TripMarkService(TripMarkDao tripMarkDao, TripService tripService) {
        this.tripMarkDao = tripMarkDao;
        this.tripService = tripService;
    }

    public TripMarkResponseDto getTripMarkById(Long id) {
        TripMark mark = tripMarkDao.getTripMarkById(id)
                .orElseThrow(() -> new TripMarkNotFoundException("Отметка с id " + id + " не найдена"));
        return toResponseDto(mark);
    }

    public TripMarkResponseDto getTripMarkByTripId(Long tripId) {
        TripMark mark = tripMarkDao.getTripMarkByTripId(tripId)
                .orElseThrow(() -> new TripMarkNotFoundException("Отметка для поездки с id " + tripId + " не найдена"));
        return toResponseDto(mark);
    }

    public TripMarkResponseDto saveOrUpdateTripMark(TripMarkRequestDto dto) {
        // Проверяем, существует ли поездка
        tripService.getTripById(dto.getTripId());  // если не существует, выбросит исключение

        Optional<TripMark> existing = tripMarkDao.getTripMarkByTripId(dto.getTripId());
        TripMark mark;
        if (existing.isPresent()) {
            // Обновляем существующую отметку
            mark = existing.get();
            if (dto.getFuelConsumed() != null) {
                mark.setFuelConsumed(dto.getFuelConsumed());
            }
            if (dto.getConditionNotes() != null) {
                mark.setConditionNotes(dto.getConditionNotes());
            }
            mark.setMarkDate(LocalDateTime.now());
            tripMarkDao.updateTripMark(mark);
        } else {
            // Создаём новую
            mark = new TripMark();
            mark.setTripId(dto.getTripId());
            mark.setFuelConsumed(dto.getFuelConsumed());
            mark.setConditionNotes(dto.getConditionNotes());
            mark.setMarkDate(LocalDateTime.now());
            tripMarkDao.insertTripMark(mark);
        }
        return toResponseDto(mark);
    }

    public void deleteTripMarkById(Long id) {
        TripMark mark = tripMarkDao.getTripMarkById(id)
                .orElseThrow(() -> new TripMarkNotFoundException("Отметка с id " + id + " не найдена"));
        tripMarkDao.deleteTripMark(id);
    }

    public void deleteTripMarkByTripId(Long tripId) {
        TripMark mark = tripMarkDao.getTripMarkByTripId(tripId)
                .orElseThrow(() -> new TripMarkNotFoundException("Отметка для поездки с id " + tripId + " не найдена"));
        tripMarkDao.deleteTripMark(mark.getId());
    }

    private TripMarkResponseDto toResponseDto(TripMark mark) {
        TripMarkResponseDto dto = new TripMarkResponseDto();
        dto.setId(mark.getId());
        dto.setTripId(mark.getTripId());
        dto.setFuelConsumed(mark.getFuelConsumed());
        dto.setConditionNotes(mark.getConditionNotes());
        dto.setMarkDate(mark.getMarkDate());
        return dto;
    }
}