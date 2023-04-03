package com.intern.fooddeliverycalculator.data.repos;

import com.intern.fooddeliverycalculator.data.dataobjects.RegionalBaseFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for RegionalBaseFee
 */
@Repository
public interface RBFRepo extends JpaRepository<RegionalBaseFee, Long> {
    /**
     * query for getting the value of the fee by city and vehicle type
     * @param city city name as a string
     * @param vehicle vehicle as a string
     * @return Double value of the fee
     */
    @Query(value = "SELECT fee from Regional_Base_Fee where city=?1 and vehicle=?2", nativeQuery = true)
    Double feeByCityAndVehicle(String city, String vehicle);

    /**
     * query for getting the RegionalBaseFee object by city and vehicle
     * @param city city name as a string
     * @param vehicle vehicle as a string
     * @return RegionalBaseFee object
     */
    @Query(value="select RegionalBaseFee from Regional_Base_Fee where city=?1 and vehicle=?2", nativeQuery = true)
    RegionalBaseFee getRBFObject(String city, String vehicle);
}
