package com.intern.fooddeliverycalculator.api.controllers;

import com.intern.fooddeliverycalculator.data.repos.WeatherRepo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(WeatherController.class)
class WeatherControllerTest {

    @Autowired
    private WeatherController weatherController;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private WeatherRepo weatherRepo;

    @Test
    void getAllWeather() {
    }

    @Test
    void getWeatherById() {
    }

    @Test
    void addWeather() {
    }

    @Test
    void updateWeatherById() {
    }

    @Test
    void deleteWeatherById() {
    }
}