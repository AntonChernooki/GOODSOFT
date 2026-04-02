package com.example.Autobase.dao;

import com.example.Autobase.model.entities.Driver;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface DriverDao {

    Optional<Driver> getDriverById(Long id);

    Optional<Driver> getDriverByUserId(Long userId);

    List<Driver> getAllDrivers();

    List<Driver> getActiveDrivers();

    void addDriver(Driver driver);

    void updateDriver(Driver driver);

    void updateDriverStatus(@Param("id") Long id, @Param("status") String status);

    void deleteDriver(Long id);


}
