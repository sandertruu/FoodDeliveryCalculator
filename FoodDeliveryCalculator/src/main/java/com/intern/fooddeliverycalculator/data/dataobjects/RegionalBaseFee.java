package com.intern.fooddeliverycalculator.data.dataobjects;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="RegionalBaseFee")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class RegionalBaseFee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String city;

    private String vehicle;

    private Double fee;
}
