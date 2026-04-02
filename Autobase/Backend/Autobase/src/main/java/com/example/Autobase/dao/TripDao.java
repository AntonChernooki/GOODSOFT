package com.example.Autobase.dao;

import com.example.Autobase.model.entities.Trip;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TripDao {
    Optional<Trip> getTripById(Long id);

    List<Trip> getAllTrip();

    List<Trip> getTripByDriverId(Long driverId);

    List<Trip> getTripByCarId(Long carId);

    List<Trip> getTripByStatus(String status);

    void addTrip(Trip trip);

    void assignDriverAndCar(@Param("id") Long id, @Param("driverId") Long driverId, @Param("carId") Long carId);

    void startTrip(@Param("id") Long id, @Param("startedAt") LocalDateTime startedAt);

    void completeTrip(@Param("id") Long id, @Param("completedAt") LocalDateTime completedAt);

    void cancelTrip(@Param("id") Long id);

    void updateTrip(Trip trip);
}
