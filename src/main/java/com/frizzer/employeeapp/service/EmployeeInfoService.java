package com.frizzer.employeeapp.service;

import com.frizzer.employeeapp.entity.EmployeeInfo;
import com.frizzer.employeeapp.repository.EmployeeInfoRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.transaction.Transactional;

@Stateless
public class EmployeeInfoService {

  @EJB
  private EmployeeInfoRepository employeeInfoRepository;

  @Transactional
  public EmployeeInfo save(EmployeeInfo employeeInfo, Long id) {
    employeeInfo.setId(id);
    return employeeInfoRepository.save(employeeInfo);
  }

  @Transactional
  public EmployeeInfo update(EmployeeInfo employeeInfo, Long id) {
    employeeInfo.setId(id);
    return employeeInfoRepository.update(employeeInfo);
  }

  @Transactional
  public boolean delete(Long id) {
    return employeeInfoRepository.delete(id);
  }

  public EmployeeInfo findById(Long id) {
    return employeeInfoRepository.findById(id);
  }
}
