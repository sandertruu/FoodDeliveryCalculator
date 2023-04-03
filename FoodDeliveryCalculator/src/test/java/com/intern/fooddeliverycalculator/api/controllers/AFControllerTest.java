package com.intern.fooddeliverycalculator.api.controllers;

import com.intern.fooddeliverycalculator.data.dataobjects.AdditionalFee;
import com.intern.fooddeliverycalculator.data.repos.AFRepo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AFController.class)
class AFControllerTest {

    @Autowired
    private AFController afController;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AFRepo afRepo;

    @Test
    void getAllAdditionalFees() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/all");
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals(0, result.getResponse().getContentLength());
    }

    @Test
    void getFeeByIdTest() throws Exception {
        AdditionalFee additionalFee = new AdditionalFee();
        additionalFee.setParameter("temperature");
        additionalFee.setCondition("-1.7");
        additionalFee.setFee(0.5);

        //when(afController.getFeeById(anyLong())).thenReturn(additionalFee); cannot solve error

        mvc.perform(MockMvcRequestBuilders.get("/getFeeById/1")).
                andExpect(MockMvcResultMatchers.jsonPath("$.parameter").value("temperature")).
                andExpect(MockMvcResultMatchers.jsonPath("$.condition").value("-1.7")).
                andExpect(MockMvcResultMatchers.jsonPath("$.fee").value(0.5)).
                andExpect(status().isOk());
    }

    @Test
    void addAdditionalFee() {
    }

    @Test
    void updateAdditionalFeeById() {
    }

    @Test
    void deleteAdditionalFeeById() {
    }

    @Test
    void updateFeeByParamAndCondition() {
    }
}