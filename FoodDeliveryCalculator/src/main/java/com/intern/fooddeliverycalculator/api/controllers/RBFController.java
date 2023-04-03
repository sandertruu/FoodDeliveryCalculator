package com.intern.fooddeliverycalculator.api.controllers;

import com.intern.fooddeliverycalculator.data.dataobjects.RegionalBaseFee;
import com.intern.fooddeliverycalculator.data.repos.RBFRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controller for the Regional Base Fees
 */
@RestController
@RequestMapping("/basefees")
public class RBFController {
    /**
     * Regional base fee repository
     */
    @Autowired
    private RBFRepo rbfRepo;

    /**
     * gets all rows from the Regional Base Fee table, localhost:9090/basefees/all
     * @return list of all the rows and httpstatus
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllFees(){
        try{
            List<RegionalBaseFee> rbfList = new ArrayList<>();
            rbfRepo.findAll().forEach(rbfList::add);

            if (rbfList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(rbfList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * get row by id from table, localhost:9090/basefees/byid/{id}
     * @param id id of the row we want as a path variable
     * @return data of the row and http status
     */
    @GetMapping("/byid/{id}")
    public ResponseEntity<RegionalBaseFee> getRBFById(@PathVariable Long id){
        Optional<RegionalBaseFee> rbfData = rbfRepo.findById(id);

        if (rbfData.isPresent()){
            return new ResponseEntity<>(rbfData.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * add a new row to the table, localhost:9090/basefees/add
     * @param rbf regionalbasefee object given in request body
     * @return created object and status
     */
    @PostMapping("/add")
    public ResponseEntity<RegionalBaseFee> addRBF(@RequestBody RegionalBaseFee rbf){
        RegionalBaseFee rbfObj = rbfRepo.save(rbf);

        return new ResponseEntity<>(rbfObj, HttpStatus.OK);
    }

    /**
     * update a row in the table, localhost:9090/basefees/update/{id}
     * @param id id of the row we want to update
     * @param newRBF new info of the row
     * @return created object and status
     */
    @PostMapping("/update/{id}")
    public ResponseEntity<RegionalBaseFee> updateRBF(@PathVariable Long id, @RequestBody RegionalBaseFee newRBF){
        Optional<RegionalBaseFee> oldRBF = rbfRepo.findById(id);

        if (oldRBF.isPresent()){
            RegionalBaseFee updatedRBF = oldRBF.get();
            updatedRBF.setCity(newRBF.getCity());
            updatedRBF.setVehicle(newRBF.getVehicle());
            updatedRBF.setFee(newRBF.getFee());

            RegionalBaseFee rbfObj = rbfRepo.save(updatedRBF);
            return new ResponseEntity<>(rbfObj, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * update method for updating row by city and vehicle, localhost:9090/basefees/updatefee
     * @param city city as a string
     * @param vehicle vehicle as a string
     * @param fee new fee as a double
     * @return updated object, status
     */
    @PostMapping("/updatefee")
    public ResponseEntity<RegionalBaseFee> updateFee(@RequestParam(name="city") String city, @RequestParam(name="vehicle") String vehicle, @RequestParam(name="newfee") Double fee){
        RegionalBaseFee regionalBaseFee = rbfRepo.getRBFObject(city, vehicle);
        if (regionalBaseFee != null){
            regionalBaseFee.setFee(fee);
            RegionalBaseFee rbfObj = rbfRepo.save(regionalBaseFee);
            return new ResponseEntity<>(rbfObj, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * delete row by id
     * @param id id of the row we wish to delete
     * @return status
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteRBF(@PathVariable Long id){
        rbfRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
