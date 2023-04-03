package com.intern.fooddeliverycalculator;

import com.intern.fooddeliverycalculator.data.dataobjects.AdditionalFee;
import com.intern.fooddeliverycalculator.data.dataobjects.RegionalBaseFee;
import com.intern.fooddeliverycalculator.data.dataobjects.Weather;
import com.intern.fooddeliverycalculator.data.repos.AFRepo;
import com.intern.fooddeliverycalculator.data.repos.RBFRepo;
import com.intern.fooddeliverycalculator.data.repos.WeatherRepo;
import com.intern.fooddeliverycalculator.logic.FeeCalculator;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FeeCalculatorTests {
    private Weather weatherTallinn;
    private Weather weatherTartu;
    private Weather weatherPärnu;

    private Long timestamp;

    private String city1;
    private String city2;
    private String city3;

    private String vehicle1;
    private String vehicle2;
    private String vehicle3;

    private RegionalBaseFee rbfTallinn1;
    private RegionalBaseFee rbfTallinn2;
    private RegionalBaseFee rbfTallinn3;
    private RegionalBaseFee rbfTartu1;
    private RegionalBaseFee rbfTartu2;
    private RegionalBaseFee rbfTartu3;
    private RegionalBaseFee rbfPärnu1;
    private RegionalBaseFee rbfPärnu2;
    private RegionalBaseFee rbfPärnu3;

    private AdditionalFee af1;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private WeatherRepo weatherRepo;

    @Autowired
    private RBFRepo rbfRepo;

    @Autowired
    private AFRepo afRepo;

    private FeeCalculator feeCalculator1;
    @Before
    public void setUp(){
        weatherTallinn = new Weather();
        weatherTartu = new Weather();
        weatherPärnu = new Weather();

        weatherTallinn.setStation("Tallinn-Harku");
        weatherTallinn.setTemperature(1.0);
        weatherTallinn.setWindspeed(2.0);
        weatherTallinn.setPhenomenon("light rain");
        weatherTallinn.setId(2L);
        weatherTallinn.setWmo(12345L);
        weatherTallinn.setTimestamp(12L);

        weatherTartu.setStation("Tartu-Tõravere");
        weatherTartu.setTemperature(-0.5);
        weatherTartu.setWindspeed(2.7);
        weatherTartu.setPhenomenon("clear");
        weatherTartu.setId(2L);
        weatherTartu.setWmo(12346L);
        weatherTartu.setTimestamp(12L);

        weatherPärnu.setStation("Pärnu");
        weatherPärnu.setTemperature(2.5);
        weatherPärnu.setWindspeed(7.0);
        weatherPärnu.setPhenomenon("overcast");
        weatherPärnu.setId(3L);
        weatherPärnu.setWmo(12347L);
        weatherPärnu.setTimestamp(12L);

        timestamp = 12L;

        city1 = "Tallinn";
        city2 = "Tartu";
        city3 = "Pärnu";

        vehicle1 = "Bike";
        vehicle2 = "Car";
        vehicle3 = "Scooter";

        rbfTallinn1 = new RegionalBaseFee(1L, city1, vehicle2, 4.0);
        rbfTallinn2 = new RegionalBaseFee(2L, city1, vehicle1, 3.0);
        rbfTallinn3 = new RegionalBaseFee(3L, city1, vehicle3, 3.5);
        rbfTartu1 = new RegionalBaseFee(4L, city2, vehicle2, 3.5);
        rbfTartu2 = new RegionalBaseFee(5L, city2, vehicle1, 2.5);
        rbfTartu3 = new RegionalBaseFee(6L, city2, vehicle3, 3.0);
        rbfPärnu1 = new RegionalBaseFee(7L, city3, vehicle2, 3.0);
        rbfPärnu2 = new RegionalBaseFee(8L, city3, vehicle1, 2.0);
        rbfPärnu3 = new RegionalBaseFee(9L, city3, vehicle3, 2.5);

        af1 = new AdditionalFee(1L, "phenomenon", "light rain", 0.5);

        entityManager.persist(rbfRepo);
        entityManager.persist(afRepo);
        entityManager.persist(weatherRepo);

        rbfRepo.save(rbfTallinn1);
        rbfRepo.save(rbfTallinn2);
        afRepo.save(af1);

        weatherRepo.save(weatherTallinn);

        feeCalculator1 = new FeeCalculator(city1, vehicle1, timestamp, rbfRepo, weatherRepo, afRepo);

    }

    @Test
    public void feeTallinnBikeRain(){

        assertEquals("3.5", feeCalculator1.calculateFee());
    }

}
