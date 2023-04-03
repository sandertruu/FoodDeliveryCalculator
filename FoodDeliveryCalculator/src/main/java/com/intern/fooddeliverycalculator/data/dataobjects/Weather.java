package com.intern.fooddeliverycalculator.data.dataobjects;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="WeatherByStations")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String station;

    private Long wmo;

    private Double temperature;

    private Double windspeed;

    private String phenomenon;

    private Long timestamp;
}
