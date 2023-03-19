package com.frizzer.employeeapp.service;

import com.frizzer.employeeapp.entity.Employee;
import com.frizzer.employeeapp.repository.EmployeeRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.transaction.Transactional;

@Stateless
public class EmployeeService {

  @EJB
  private EmployeeRepository employeeRepository;

  @Transactional
  public Employee save(Employee employee) {
    return employeeRepository.save(employee);
  }

  public Employee findById(Long id) {
    return employeeRepository.findById(id);
  }
}
