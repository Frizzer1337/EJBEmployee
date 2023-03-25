package com.frizzer.employeeapp.repository;

import com.frizzer.employeeapp.entity.employee.Employee;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Stateless
public class EmployeeRepository extends AbstractRepository<Employee> {


  public Employee findByLogin(String login) {
    final String SELECT_BY_LOGIN = "SELECT e FROM Employee e WHERE e.login = :login";
    TypedQuery<Employee> query = entityManager.createQuery(SELECT_BY_LOGIN, Employee.class);
    query.setParameter("login", login);
    return query.getSingleResult();
  }

  public List<Employee> findAll() {
    return entityManager.createQuery("from Employee", Employee.class).getResultList();
  }

  @Override
  public Class<Employee> getEntityClass() {
    return Employee.class;
  }

}
