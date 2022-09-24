package com.example.obspringsecurityjwt.dto;

import com.example.obspringsecurityjwt.domain.Car;

import java.util.List;

public class CarListDTO {


    private List<Car> cars;

    public CarListDTO(){}


    public List<Car> getCars() {

        return cars;
    }

    public void setCars(List<Car> cars){

        this.cars = cars;
    }



}
