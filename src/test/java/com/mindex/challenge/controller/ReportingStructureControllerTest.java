package com.mindex.challenge.controller;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.reporting.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ReportingStructureControllerTest {
  @InjectMocks
  private ReportingStructureController reportingStructureController;
  @Mock
  private ReportingStructureService mockReportingStructureService;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(reportingStructureController).build();
  }

  @Test
  public void testGetReportingStructure() throws Exception {
    Employee employee = new Employee();
    String employeeId = "9999";
    employee.setEmployeeId(employeeId);
    int numberOfReports = 0;
    ReportingStructure reportingStructure = new ReportingStructure(employee, numberOfReports);

    when(mockReportingStructureService.getReportingStructure(anyString())).thenReturn(reportingStructure);

    ResultActions performResults = mockMvc.perform(MockMvcRequestBuilders.get("/reporting/{employeeId}", employeeId));
    performResults.andDo(MockMvcResultHandlers.print());
    performResults.andExpect(MockMvcResultMatchers.status().isOk());
    performResults.andExpect(MockMvcResultMatchers.jsonPath("$.employee.employeeId").value(employeeId));
  }
}