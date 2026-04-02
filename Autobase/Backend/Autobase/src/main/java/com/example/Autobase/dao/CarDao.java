package com.example.Autobase.dao;

import com.example.Autobase.model.entities.Car;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;


public interface CarDao {

    Optional<Car> getCarById(Long id);

    Optional<Car> getCarByStateNumber(String stateNumber);

    List<Car> getAllCar();

    List<Car> getCarByStatus(String status);

    List<Car> getCarByMark(String mark);

    List<Car> getCarByColor(String color);

    List<Car> getCarByYearOfRelease(Integer yearOfRelease);


    void updateCar(Car car);

    void updateCarStatus(@Param("id") Long id, @Param("status") String status);

    void deleteCar(Long id);

    void addCar(Car car);

}



