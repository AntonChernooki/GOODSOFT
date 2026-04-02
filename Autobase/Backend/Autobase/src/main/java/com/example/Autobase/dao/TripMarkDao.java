package com.example.Autobase.dao;

import com.example.Autobase.model.entities.TripMark;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

public interface TripMarkDao {
    Optional<TripMark> getTripMarkById(Long id);
    Optional<TripMark> getTripMarkByTripId(Long tripId);
    void insertTripMark(TripMark mark);
    void updateTripMark(TripMark mark);
    void deleteTripMark(@Param("id") Long id);
}