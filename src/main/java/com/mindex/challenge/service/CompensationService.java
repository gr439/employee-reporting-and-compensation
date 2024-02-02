package com.mindex.challenge.service;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompensationService {
  @Autowired
  private CompensationRepository compensationRepository;

  public List<Compensation> findAll() {
    return compensationRepository.findAll();
  }

  public Compensation findByEmployeeId(String employeeId) {
    return compensationRepository.findByEmployeeId(employeeId);
  }

  public Compensation save(Compensation compensation) {
    return compensationRepository.save(compensation);
  }

  public void deleteByEmployeeId(String employeeId) {
    compensationRepository.deleteByEmployeeId(employeeId);
  }

}