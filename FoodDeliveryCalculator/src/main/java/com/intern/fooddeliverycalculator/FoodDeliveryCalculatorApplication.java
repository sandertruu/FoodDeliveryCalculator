package com.intern.fooddeliverycalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FoodDeliveryCalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodDeliveryCalculatorApplication.class, args);
    }

}
