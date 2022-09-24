package com.example.obspringsecurityjwt.service;


import com.example.obspringsecurityjwt.domain.Car;
import com.example.obspringsecurityjwt.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

    private static final Integer MIN_DOORS =3;

    private final Logger log = LoggerFactory.getLogger(CarServiceImpl.class);


    private CarRepository carRepository;



    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;

    }

    @Override
    public List<Car> findAll() {
        log.info("Executing findAll Cars");
        return this.carRepository.findAll();
    }

    @Override
    public Optional<Car> findById(Long id) {
        log.info("executing findById");
        return this.carRepository.findById(id);
    }


    @Override
    public  List<Car> findByDoors(Integer doors) {
        log.info("Searching cars by doors");
        if(doors < MIN_DOORS) {
            log.info("Trying o sarch less than allowed doors");
            return new ArrayList<>();
        }

        return this.carRepository.findByDoors(doors);

    }

    @Override
    public Long count() {
        return null;
    }





    @Override
    public Car save(Car car) {
        log.info("Creating / updating car");
        // pre
        if(!this.validateCar(car))
            return null;

        // actions
        // find template from db
        Car carDB = this.carRepository.save(car);

        // post:
        // enviar notificacion
        // this.notificationService(NotificationType.CREATION, car);

        return carDB;
    }

    private boolean validateCar(Car car) {
        //car null validation
        if(car == null){
            log.warn("trying to create null car");
            return false;
        }
        // num doors validation
        if(car.getDoors() == null || car.getDoors() < MIN_DOORS){
            log.warn("trying to create car with not allowed number of doors");
            return false;
        }

        // color validation
        // .........

        return true;

    }

    @Override
    public void deleteById(Long id) {
        log.info("deleting car by id ");
        if(id == null || id < 0 || id == 0){
            log.warn("trying to delete car with wrong id " );
            return;
        }

        try{
            this.carRepository.delete(id);
        } catch (Exception e) {
            log.error("Error trying to delete car by id {}", id, e);
        }
    }

    @Override
    public void deleteAll() {
        log.info("Deleting cars") ;
        this.carRepository.deleteAll();
    }


    @Override
    public void deleteAll(List<Car> cars) {
        log.info("Deleting car by id ");
        if(CollectionUtils.isEmpty(cars)) {
            log.warn("trying to delete an ampty or null car list");
            return;
        }
        this. carRepository.deleteAll(cars);

    }

    @Override
    public void deleteAllById(List<Long> ids) {
        log.info("Deleting car by id ");
        if(CollectionUtils.isEmpty(ids)) {
            log.warn("trying to delete am empty or null car list");
            return;
        }

        this.carRepository.deleteAllById(ids);
    }


    @Override
    public List<Car> findByManufacturerAndModel(String manufacturer, String model) {
        return null;
    }


    @Override
    public List<Car> findByDoorsGreaterThanEqual(Integer doors) {
        if(doors == null || doors < 0)
            return new ArrayList<>();

        return this.carRepository.findByDoorsGreaterThanEqual(doors);
    }



    @Override
    public List<Car> findByModelContaining(String model) {
        return this.carRepository.findByModelContaining(model);

    }

    @Override
    public List<Car> findByYearIn(List<Integer> years){
        return this.carRepository.findByYearIn(years);
    }

    @Override
    public List<Car>findByYearBetween(Integer starYear, Integer endYear){
        return this.carRepository.findByYearBetween(starYear, endYear);
    }


    @Override
    public List<Car>findByReleaseDateBetween(LocalDate starDate, LocalDate endDate){

        return this.carRepository.findByReleaseDateBetween(starDate, endDate);
    }


    @Override
    public List<Car> findByAvailableTrue() {
        return this.carRepository.findByAvailableTrue();
    }

    @Override
    public Long deleteAllByAvailableFalse(){
        return this.carRepository.deleteAllByAvailableFalse();
    }

}
