package com.intern.fooddeliverycalculator.data.repos;

import com.intern.fooddeliverycalculator.data.dataobjects.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepo extends JpaRepository<Weather, Long> {
    @Query(value = "SELECT * from Weather_by_stations where station=?1 and timestamp=?2", nativeQuery = true)
    Weather findByStation(String station, Long timestamp);
}
