package com.intern.fooddeliverycalculator.api.controllers;

import com.intern.fooddeliverycalculator.data.dataobjects.Weather;
import com.intern.fooddeliverycalculator.data.repos.AFRepo;
import com.intern.fooddeliverycalculator.data.repos.RBFRepo;
import com.intern.fooddeliverycalculator.data.repos.WeatherRepo;
import com.intern.fooddeliverycalculator.logic.FeeCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * controller for the Weather table
 */
@RestController
public class WeatherController {
    /**
     * including all repositories, since the controller includes calculating request
     */
    @Autowired
    private WeatherRepo weatherRepo;

    @Autowired
    private RBFRepo rbfRepo;

    @Autowired
    private AFRepo afRepo;
    /**
     * current time stamp that changes every time data is read from Ilmateenistus.
     */
    private Long currentTimestamp;

    /**
     * gets all rows from Weather table, localhost:9090/allweather
     * @return list of rows and status
     */
    @GetMapping("/allweather")
    public ResponseEntity<List<Weather>> getAllWeather(){
        try {
            List<Weather> weatherList = new ArrayList<>();
            weatherRepo.findAll().forEach(weatherList::add);

            if (weatherList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(weatherList, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * get row by id from table, localhost:9090/weatherbyid/{id}
     * @param id id of the row we wish to get
     * @return data of the row and status
     */
    @GetMapping("/weatherbyid/{id}")
    public ResponseEntity<Weather> getWeatherById(@PathVariable Long id){
        Optional<Weather> weatherData = weatherRepo.findById(id);

        if (weatherData.isPresent()){
            return new ResponseEntity<>(weatherData.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Add a row to the table, localhost:9090/addweather
     * @param weather data of the weather object in a request body
     * @return created object and status
     */
    @PostMapping("/addweather")
    public ResponseEntity<Weather> addWeather(@RequestBody Weather weather){
        Weather weatherObj = weatherRepo.save(weather);

        return new ResponseEntity<>(weatherObj, HttpStatus.OK);
    }

    /**
     * update row by id, localhost:9090/updateweather/{id}
     * @param id id of the row we wish to update
     * @param newWeatherData new data for the update of the row
     * @return updated object, status
     */
    @PostMapping("/updateweather/{id}")
    public ResponseEntity<Weather> updateWeatherById(@PathVariable Long id, @RequestBody Weather newWeatherData){
        Optional<Weather> oldWeatherData = weatherRepo.findById(id);

        if (oldWeatherData.isPresent()){
            Weather updatedWeatherData = oldWeatherData.get();
            updatedWeatherData.setStation(newWeatherData.getStation());
            updatedWeatherData.setWmo(newWeatherData.getWmo());
            updatedWeatherData.setTemperature(newWeatherData.getTemperature());
            updatedWeatherData.setWindspeed(newWeatherData.getWindspeed());
            updatedWeatherData.setPhenomenon(newWeatherData.getPhenomenon());
            updatedWeatherData.setTimestamp(newWeatherData.getTimestamp());

            Weather weatherObj = weatherRepo.save(updatedWeatherData);
            return new ResponseEntity<>(weatherObj, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    /**
     * delete row by id, localhost:9090/deleteweather/{id}
     * @param id id of the row we want to delete
     * @return status
     */
    @DeleteMapping("/deleteweather/{id}")
    public ResponseEntity<HttpStatus> deleteWeatherById(@PathVariable Long id) {
        weatherRepo.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * request for calculating the delivery fee for given city and vehicle type, localhost:9090/calculatefee
     * @param city city as a string
     * @param vehicle vehicle as a string
     * @return the result of the calculation as string and status
     */
    @GetMapping("/calculatefee")
    public ResponseEntity<String> calculateFee(@RequestParam(name="city") String city, @RequestParam(name="vehicle") String vehicle){
        FeeCalculator calc = new FeeCalculator(city, vehicle, currentTimestamp, rbfRepo, weatherRepo, afRepo);
        return new ResponseEntity<>(calc.calculateFee(), HttpStatus.OK);
    }

    /**
     * Method that imports data from ilmateenistus.ee every 15 minutes past full hour using Scheduled which is configureable
     * also saves the current timestamp, so we could get the latest weather data when requesting delivery fee calculation
     * @throws Exception
     */
    @Scheduled(cron = "0 15 * * * *")
    public void importData() throws Exception{
        URL url = new URL("https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php");
        List<String> stations = Arrays.asList("Tallinn-Harku", "Tartu-Tõravere", "Pärnu");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        URLConnection connection = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        Document document = builder.parse(new InputSource(reader));
        document.getDocumentElement().normalize();
        String timestamp = document.getDocumentElement().getAttribute("timestamp");
        currentTimestamp = Long.parseLong(timestamp);
        NodeList stationNodes = document.getElementsByTagName("station");

        for (int i = 0; i < stationNodes.getLength(); i++) {
            Node stationNode = stationNodes.item(i);
            if (stationNode.getNodeType() == Node.ELEMENT_NODE){
                Element stationElement = (Element) stationNode;
                String stationName = stationElement.getElementsByTagName("name").item(0).getTextContent();
                if (stations.contains(stationName)){
                    String wmo = stationElement.getElementsByTagName("wmocode").item(0).getTextContent();
                    String temperature = stationElement.getElementsByTagName("airtemperature").item(0).getTextContent();
                    String windspeed = stationElement.getElementsByTagName("windspeed").item(0).getTextContent();
                    String phenomenon = stationElement.getElementsByTagName("phenomenon").item(0).getTextContent();

                    Weather weather = new Weather();

                    weather.setStation(stationName);
                    weather.setPhenomenon(phenomenon);
                    weather.setTemperature(Double.parseDouble(temperature));
                    weather.setTimestamp(Long.parseLong(timestamp));
                    weather.setWindspeed(Double.parseDouble(windspeed));
                    weather.setWmo(Long.parseLong(wmo));

                    weatherRepo.save(weather);
                }
            }
        }
    }

}
