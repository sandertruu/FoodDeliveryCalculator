package com.intern.fooddeliverycalculator.api.controllers;

import com.intern.fooddeliverycalculator.data.repos.RBFRepo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RBFController.class)
class RBFControllerTest {

    @Autowired
    private RBFController rbfController;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RBFRepo rbfRepo;

    @Test
    void getAllFees() {
    }

    @Test
    void getRBFById() {
    }

    @Test
    void addRBF() {
    }

    @Test
    void updateRBF() {
    }

    @Test
    void updateFee() {
    }

    @Test
    void deleteRBF() {
    }
}