package com.example.obspringsecurityjwt.repository;

import com.example.obspringsecurityjwt.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface CarRepository extends JpaRepository<Car,Long> {

    List<Car> findByDoors(Integer doors);

    List<Car> findByManufacturerAndModel(String manufacturer, String model);

    List<Car> findByDoorsGreaterThanEqual(Integer doors);

    List<Car> findByModelContaining(String model);

    List<Car> findByYearIn(List<Integer> years);

    List<Car> findByYearBetween(Integer startYear, Integer endYear);

    List<Car> findByAvailableTrue();

    Long deleteAllByAvailableFalse();


    List<Car> findByReleaseDateBetween(LocalDate starDate, LocalDate endDate);


}
