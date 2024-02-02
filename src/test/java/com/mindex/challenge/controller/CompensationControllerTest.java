package com.mindex.challenge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CompensationControllerTest {
  @InjectMocks
  private CompensationController compensationController;
  @Mock
  private CompensationService mockCompensationService;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(compensationController).build();
  }

  @Test
  public void show() throws Exception {
    String employeeId = "9999";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = simpleDateFormat.parse("2024-02-03");
    Compensation compensation = new Compensation(employeeId, 9999.99f, date);
    when(mockCompensationService.findByEmployeeId("9999")).thenReturn(compensation);

    mockMvc.perform(MockMvcRequestBuilders.get("/compensations/{id}", employeeId)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isAccepted())
        .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").value("9999"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value("9999.99"));
  }

  @Test
  public void create() throws Exception {
    String employeeId = "9999";
    float salary = 9999.99f;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = simpleDateFormat.parse("2024-02-03");
    Compensation compensation = new Compensation(employeeId, salary, date);

    mockMvc.perform(MockMvcRequestBuilders.post("/compensations")
        .content(new ObjectMapper().writeValueAsString(compensation))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isAccepted());
  }

}