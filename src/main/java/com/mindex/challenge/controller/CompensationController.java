package com.mindex.challenge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("compensations")
@RestController
public class CompensationController {
  @Autowired
  private CompensationService compensationService;

  //list all compensations
  @GetMapping(value = {"/",""})
  public ResponseEntity<Iterable<Compensation>> index (HttpServletRequest request, HttpServletResponse response) {
    Iterable<Compensation> compensations=compensationService.findAll();
    ResponseEntity<Iterable<Compensation>> responseEntity = new ResponseEntity<>(compensations, HttpStatus.ACCEPTED);
    return responseEntity;
  }

  //get compensation by employee id
  @GetMapping(value = "/{id}")
  public ResponseEntity<Compensation> show (@PathVariable("id") String idString) {
    Compensation compensation = compensationService.findByEmployeeId(idString);
    ResponseEntity<Compensation> responseEntity = new ResponseEntity<>(compensation, HttpStatus.ACCEPTED);
    return responseEntity;
  }

  //create compensation containing employeeId, salary, effectiveDate
  //if the record exists under employeeId, the old data will be deleted then a new one created
  @PostMapping(value = {"/",""})
  public ResponseEntity<String> create (@RequestBody Compensation compensation) throws JsonProcessingException {
    if (compensationService.findByEmployeeId(compensation.getEmployeeId())!=null)
      compensationService.deleteByEmployeeId(compensation.getEmployeeId());
    Compensation result = compensationService.save(compensation);
    ResponseEntity<String> responseEntity = null;
    ObjectMapper mapper = new ObjectMapper();
    responseEntity = new ResponseEntity<>(mapper.writeValueAsString(result), HttpStatus.ACCEPTED);
    return responseEntity;
  }

}