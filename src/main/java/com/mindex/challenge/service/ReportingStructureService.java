package com.mindex.challenge.service;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.reporting.ReportingStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

@Service
public class ReportingStructureService {

  private final EmployeeRepository employeeRepository;

  public ReportingStructureService(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  public ReportingStructure getReportingStructure(String employeeId) {
    Employee employee = employeeRepository.findByEmployeeId(employeeId);
    int numberOfReports = countDirectReportsByIteration(employee);
    ReportingStructure reportingStructure = new ReportingStructure(employee, numberOfReports);
    return reportingStructure;
  }

  public int countDirectReportsByIteration(Employee employee) {
    int count = 0;
    if (employee.getDirectReports() != null) {
      Deque<Employee> queue = new ArrayDeque<>();
      queue.push(employee); //will pop this entry first time in loop, which is the top parent employee, and will not be added to the count

      while (!queue.isEmpty()) {
        Employee currentEmployee = queue.pop();
        List<Employee> directReports = currentEmployee.getDirectReports();
        if (directReports != null) {
          for (Employee directReport : directReports) {
            Employee directReportEmployee = employeeRepository.findByEmployeeId(directReport.getEmployeeId());
            if (directReportEmployee != null) {
              count++;
              queue.push(directReportEmployee);
            }
          }
        }
      }
    }
    return count;
  }

  private int countDirectReportsByRecursion(Employee employee) {
    int count = 0;
    if (employee.getDirectReports() != null) {
      for (Employee directReport : employee.getDirectReports()) {
        Employee directReportEmployee = employeeRepository.findByEmployeeId(directReport.getEmployeeId());
        if (directReportEmployee != null) {
          count++;
          count += countDirectReportsByRecursion(directReportEmployee);
        }
      }
    }
    return count;
  }

}