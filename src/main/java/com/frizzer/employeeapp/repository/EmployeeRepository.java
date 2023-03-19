package com.frizzer.employeeapp.repository;

import com.frizzer.employeeapp.entity.Employee;
import jakarta.ejb.Stateless;

@Stateless
public class EmployeeRepository extends AbstractRepository<Employee> {
  @Override
  public Class<Employee> getEntityClass() {
    return Employee.class;
  }

}
