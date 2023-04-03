package com.intern.fooddeliverycalculator.api.controllers;

import com.intern.fooddeliverycalculator.data.dataobjects.AdditionalFee;
import com.intern.fooddeliverycalculator.data.repos.AFRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controller for Additional Fees
 */
@RestController
@RequestMapping("/additionalfees")
public class AFController {

    /**
     * JPA repository for additional fees
     */
    @Autowired
    private AFRepo afRepo;

    /**
     * gets all rows from Additional_fees table, request at localhost:9090/additionalfees/all
     * @return list of all rows from the table with http status
     */
    @GetMapping("/all")
    public ResponseEntity<List<AdditionalFee>> getAllAdditionalFees(){
        try{
            List<AdditionalFee> feeList = new ArrayList<>();
            afRepo.findAll().forEach(feeList::add);

            if (feeList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(feeList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * find row by id, basic method to manipulate the data, request at localhost:9090/additionalfees/getFeeById/{id}
     * @param id id of the row given as a path variable
     * @return item with given id or NOT_FOUND status if item is not present.
     */
    @GetMapping("/getFeeById/{id}")
    public ResponseEntity<AdditionalFee> getFeeById(@PathVariable Long id){
        Optional<AdditionalFee> feeData = afRepo.findById(id);

        if(feeData.isPresent()){
            return new ResponseEntity<>(feeData.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * add a single AdditionalFee object to table, localhost:9090/additionalfees/add
     * @param additionalFee AdditionalFee object given in request body
     * @return created object with status message
     */
    @PostMapping("/add")
    public ResponseEntity<AdditionalFee> addAdditionalFee(@RequestBody AdditionalFee additionalFee){
        AdditionalFee afObj = afRepo.save(additionalFee);

        return new ResponseEntity<>(afObj, HttpStatus.OK);
    }

    /**
     * updates a row in the table by id, localhost/additionalfees/update/{id}
     * @param id id of the row we want to change
     * @param newAdditionalFee new parameters for the row we want to change
     * @return updated dataobject and status
     */
    @PostMapping("/update/{id}")
    public ResponseEntity<AdditionalFee> updateAdditionalFeeById(@PathVariable Long id, @RequestBody AdditionalFee newAdditionalFee){
        Optional<AdditionalFee> oldAFData = afRepo.findById(id);

        if (oldAFData.isPresent()){
            AdditionalFee updatedAFData = oldAFData.get();
            updatedAFData.setFee(newAdditionalFee.getFee());
            updatedAFData.setParameter(newAdditionalFee.getParameter());
            updatedAFData.setCondition(newAdditionalFee.getCondition());

            AdditionalFee afObj = afRepo.save(updatedAFData);
            return new ResponseEntity<>(afObj, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * delete row from table by ID, localhost/additonalfees/delete/{id}
     * @param id id of the row we wish to delete
     * @return status message
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteAdditionalFeeById(@PathVariable Long id){
        afRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * additional update method, which takes weather parameter and condition and updates the fee, localhost:9090/additionalfees/updatefee
     * @param param weather parameter (temperature, windspeed, phenomenon, ....)
     * @param condition condition as string, for example temperature as string or phenomenon
     * @param fee new fee we wish to set for a row
     * @return updated object and status
     */
    @PostMapping("/updatefee")
    public ResponseEntity<AdditionalFee> updateFeeByParamAndCondition(@RequestParam(name="parameter") String param, @RequestParam(name = "condition") String condition, @RequestParam(name="fee") Double fee){
        AdditionalFee additionalFee = afRepo.getAdditionalFee(param, condition);
        if (additionalFee != null){
            additionalFee.setFee(fee);
            AdditionalFee afObj = afRepo.save(additionalFee);
            return new ResponseEntity<>(afObj, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
