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
    entityManager.persist(employee);
    return employee;
  }

  public Employee findById(Long id) {
    return entityManager.find(Employee.class, id);
  }

  public Employee update(Employee employee, Long id) {
    employee.setId(id);
    return entityManager.merge(employee);
  }

  public boolean delete(Long id) {
    Employee employee = findById(id);
    if (employee == null) {
      return false;
    } else {
      entityManager.remove(employee);
      return true;
    }
  }

}
