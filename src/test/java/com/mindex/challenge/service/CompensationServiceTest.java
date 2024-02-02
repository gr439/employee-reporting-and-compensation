package com.mindex.challenge.service;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CompensationServiceTest {
  @InjectMocks
  private CompensationService compensationService;
  @Mock
  private CompensationRepository mockCompensationRepository;

  @Test
  public void testFindAll() throws ParseException {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = simpleDateFormat.parse("2024-02-03");
    List<Compensation> compensations = new ArrayList<>();
    compensations.add(new Compensation("9999", 9999.99f, date));
    compensations.add(new Compensation("8888", 8888.88f, date));

    //mock database invocation
    when(mockCompensationRepository.findAll()).thenReturn(compensations);

    //invoke system under test
    List<Compensation> result = compensationService.findAll();

    assertEquals(compensations.size(), result.size());
    for (int i = 0; i < compensations.size(); i++) {
      assertEquals(compensations.get(i), result.get(i));
    }
  }

  @Test
  public void testFindByEmployeeIdExisting() throws ParseException {
    //setup testing variables
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = simpleDateFormat.parse("2024-02-03");
    String employeeId = "9999";
    Compensation compensation = new Compensation(employeeId, 9999.99f, date);

    //mock database invocation
    when(mockCompensationRepository.findByEmployeeId(employeeId)).thenReturn(compensation);

    //invoke system under test
    Compensation result = compensationService.findByEmployeeId(employeeId);

    //assert results
    assertNotNull(result);
    assertEquals(compensation, result);
  }

  @Test
  public void testFindByEmployeeIdNonExisting() throws ParseException {
    //setup testing variables
    String employeeId = "9999";;

    //mock database invocation
    when(mockCompensationRepository.findByEmployeeId(employeeId)).thenReturn(null);

    //invoke system under test
    Compensation result = compensationService.findByEmployeeId(employeeId);

    //assert results
    assertNull(result);
  }

  @Test
  public void testSave() throws ParseException {
    //setup testing variables
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = simpleDateFormat.parse("2024-02-03");
    String employeeId = "9999";
    Compensation compensation = new Compensation(employeeId, 9999.99f, date);

    //mock database invocation
    when(mockCompensationRepository.save(compensation)).thenReturn(compensation);

    //invoke system under test
    Compensation result = compensationService.save(compensation);

    //assert results
    assertEquals(compensation, result);
  }

  @Test
  public void testDeleteByEmployeeId() {
    String employeeId = "9999";

    //invoke system under test
    compensationService.deleteByEmployeeId(employeeId);

    //verify that the method compensationRepository.deleteByEmployeeId(Employee) was invoked at least once
    verify(mockCompensationRepository).deleteByEmployeeId(employeeId);
  }
}