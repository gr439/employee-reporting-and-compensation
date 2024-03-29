package com.mindex.challenge.controller;

import com.mindex.challenge.data.reporting.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportingStructureController {

  private final ReportingStructureService reportingStructureService;

  public ReportingStructureController(ReportingStructureService reportingStructureService) {
    this.reportingStructureService = reportingStructureService;
  }

  @GetMapping("/reporting/{employeeId}")
  public ReportingStructure getReportingStructure(@PathVariable String employeeId) {
    // Invoke business logic to retrieve ReportingStructure based on employeeId
    return reportingStructureService.getReportingStructure(employeeId);
  }

}
