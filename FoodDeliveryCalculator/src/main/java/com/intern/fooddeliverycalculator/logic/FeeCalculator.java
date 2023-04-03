package com.intern.fooddeliverycalculator.logic;

import com.intern.fooddeliverycalculator.VehicleForbiddenException;
import com.intern.fooddeliverycalculator.data.dataobjects.Weather;
import com.intern.fooddeliverycalculator.data.repos.AFRepo;
import com.intern.fooddeliverycalculator.data.repos.RBFRepo;
import com.intern.fooddeliverycalculator.data.repos.WeatherRepo;

import java.util.Arrays;
import java.util.List;

public class FeeCalculator {


    private final RBFRepo rbfRepo;

    private final WeatherRepo weatherRepo;

    private final AFRepo afRepo;

    private final String city;
    private final String vehicle;
    private final Long timestamp;


    public FeeCalculator(String city, String vehicle, Long timestamp, RBFRepo rbfRepo, WeatherRepo weatherRepo, AFRepo afRepo) {
        this.city = city;
        this.vehicle = vehicle;
        this.timestamp = timestamp;
        this.weatherRepo = weatherRepo;
        this.rbfRepo = rbfRepo;
        this.afRepo = afRepo;
    }

    public String calculateFee(){
        List<String> vehicles = Arrays.asList("Car", "Bike", "Scooter");
        if (!vehicles.contains(vehicle)){
            return String.valueOf(new VehicleForbiddenException("Usage of selected vehicle type is forbidden"));
        }
        double total = rbfRepo.feeByCityAndVehicle(city, vehicle);
        String station = "";
        switch (city) {
            case "Tallinn":
                station = "Tallinn-Harku";
                break;
            case "Tartu":
                station = "Tartu-Tõravere";
                break;
            case "Pärnu":
                station = "Pärnu";
                break;
            default:
                return "This city is not in our application!";
        }
        Weather weather = weatherRepo.findByStation(station, timestamp);
        double temp = weather.getTemperature();
        double wind = weather.getWindspeed();
        String phen = weather.getPhenomenon();
        double atef = 0;
        double wsef = 0;
        double wpef = 0;

        if (vehicle.equals("Bike") || vehicle.equals("Scooter")){
            if(temp < -10){
                atef = 1.0;
            }else if (temp < 0){
                atef = 0.5;
            }else{
                atef = 0;
            }
            if(afRepo.getAdditionalFeeValue("phenomenon", phen) == null){
                wpef = 0;
            }else if (afRepo.getAdditionalFeeValue("phenomenon", phen) == -1){
                return String.valueOf(new VehicleForbiddenException("Usage of selected vehicle type is forbidden"));
            }
            else{
                wpef = afRepo.getAdditionalFeeValue("phenomenon", phen);
            }
        }

        if (vehicle.equals("Bike")){
            if (wind > 10 && wind < 20){
                return String.valueOf(new VehicleForbiddenException("Usage of selected vehicle type is forbidden"));
            }else if (wind < 10){
                wsef = 0.5;
            }else{
                wsef = 0;
            }
        }


        total = total + atef + wpef + wsef;
        return String.valueOf(total);
    }
}
