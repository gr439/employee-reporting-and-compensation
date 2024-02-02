package com.mindex.challenge.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.reporting.ReportingStructure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ReportingStructureServiceTest {
  @InjectMocks
  private ReportingStructureService reportingStructureService;
  @Mock
  private EmployeeRepository mockEmployeeRepository;

  //test the helper method when there is an employee with no direct reports
  @Test
  public void testCountDirectReportsByIteration_whenNoDirectReports() {
    Employee employee = new Employee();
    employee.setEmployeeId("9999");
    employee.setDirectReports(null);

    assertEquals(0, reportingStructureService.countDirectReportsByIteration(employee));
  }

  //test the helper method when there is an employee with 1 direct report
  @Test
  public void testCountDirectReportsByIteration_whenOneDirectReport() {
    Employee employee = new Employee();
    employee.setEmployeeId("9999");
    Employee directReport = new Employee();
    directReport.setEmployeeId("8888");
    employee.setDirectReports(List.of(directReport));

    when(mockEmployeeRepository.findByEmployeeId("8888")).thenReturn(directReport);

    assertEquals(1, reportingStructureService.countDirectReportsByIteration(employee));
  }

  //test the helper method when the employee has a direct report that also has a direct report
  @Test
  public void testCountDirectReportsByIteration_whenOneDirectReportsAlsoReports() {
    Employee employee = new Employee(); //employee id 9999 has direct report 8888, 8888 has direct report 7777
    employee.setEmployeeId("9999");
    Employee directReportOfEmployee = new Employee();
    directReportOfEmployee.setEmployeeId("8888");
    employee.setDirectReports(List.of(directReportOfEmployee));

    Employee directReportOfDirectReport = new Employee();
    directReportOfDirectReport.setEmployeeId("7777");
    directReportOfEmployee.setDirectReports(List.of(directReportOfDirectReport));

    when(mockEmployeeRepository.findByEmployeeId("8888")).thenReturn(directReportOfEmployee);
    when(mockEmployeeRepository.findByEmployeeId("7777")).thenReturn(directReportOfDirectReport);

    assertEquals(2, reportingStructureService.countDirectReportsByIteration(employee));
  }

  //test the helper method when the employee has multiple direct reports
  @Test
  public void testCountDirectReportsByIteration_whenMultipleDirectReports() {
    Employee employee = new Employee();
    Employee directReport1 = new Employee();
    Employee directReport2 = new Employee();
    directReport1.setEmployeeId("9999");
    directReport2.setEmployeeId("8888");
    employee.setDirectReports(List.of(directReport1, directReport2));

    when(mockEmployeeRepository.findByEmployeeId("9999")).thenReturn(directReport1);
    when(mockEmployeeRepository.findByEmployeeId("8888")).thenReturn(directReport2);

    assertEquals(2, reportingStructureService.countDirectReportsByIteration(employee));
  }

  //test the endpoint method when the employee has no direct reports
  @Test
  public void testGetReportingStructureNoDirectReports() {
    String employeeId = "9999";

    Employee employee = new Employee();
    employee.setEmployeeId(employeeId);
    when(mockEmployeeRepository.findByEmployeeId(employeeId)).thenReturn(employee);

    ReportingStructure reportingStructure = reportingStructureService.getReportingStructure(employeeId);

    assertEquals(employee, reportingStructure.getEmployee());
    assertEquals(0, reportingStructure.getNumberOfReports());
  }

  //test the endpoint method when the employee has one direct report
  @Test
  public void testGetReportingStructureOneDirectReport() {
    String employeeId = "9999";

    Employee employee = new Employee();
    employee.setEmployeeId(employeeId);
    Employee directReport = new Employee();
    directReport.setEmployeeId("8888");
    employee.setDirectReports(List.of(directReport));

    //when it searches for employee id's 9999, and 8888, then return mock record imitating database retrieval
    when(mockEmployeeRepository.findByEmployeeId(employeeId)).thenReturn(employee);
    when(mockEmployeeRepository.findByEmployeeId(directReport.getEmployeeId())).thenReturn(directReport);

    ReportingStructure reportingStructure = reportingStructureService.getReportingStructure(employeeId);

    assertEquals(employee, reportingStructure.getEmployee());
    assertEquals(1, reportingStructure.getNumberOfReports());
  }

}
