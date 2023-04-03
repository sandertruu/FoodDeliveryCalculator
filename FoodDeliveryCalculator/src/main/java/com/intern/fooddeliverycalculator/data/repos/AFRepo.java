package com.intern.fooddeliverycalculator.data.repos;


import com.intern.fooddeliverycalculator.data.dataobjects.AdditionalFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository for Additional fees
 */
@Repository
public interface AFRepo extends JpaRepository<AdditionalFee, Long> {
    /**
     * query for getting fee by parameter and condition
     * @param parameter weather parameter, for example temperature, phenomenon
     * @param condition value of the parameter as a string
     * @return Double value of fee
     */
    @Query(value = "select fee from Additional_fees where parameter=?1 and condition=?2", nativeQuery = true)
    Double getAdditionalFeeValue(String parameter, String condition);

    /**
     * query for getting the AdditionaFee object by parameter and condition
     * @param parameter weather parameter, for example temperature, phenomenon
     * @param condition value of the parameter as a string
     * @return AdditionalFee object
     */
    @Query(value="select AdditionalFee from Additional_fees where parameter=?1 and condition=?2", nativeQuery = true)
    AdditionalFee getAdditionalFee(String parameter, String condition);
}
