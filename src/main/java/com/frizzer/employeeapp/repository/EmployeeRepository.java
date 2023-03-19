package com.frizzer.employeeapp.repository;

import com.frizzer.employeeapp.entity.Employee;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class EmployeeRepository {

  @PersistenceContext(unitName = "default")
  private EntityManager entityManager;

  public Employee save(Employee employee) {
    return entityManager.merge(employee);
  }

  public Employee findById(Long id) {
    return entityManager.find(Employee.class, id);
  }

}
