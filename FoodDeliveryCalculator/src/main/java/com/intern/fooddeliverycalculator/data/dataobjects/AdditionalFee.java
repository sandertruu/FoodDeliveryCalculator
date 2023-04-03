package com.intern.fooddeliverycalculator.data.dataobjects;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="AdditionalFees")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class AdditionalFee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String parameter;

    private String condition;

    private Double fee;

}
