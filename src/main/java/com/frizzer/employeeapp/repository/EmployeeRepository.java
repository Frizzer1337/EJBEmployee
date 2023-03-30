package com.frizzer.employeeapp.repository;

import com.frizzer.employeeapp.entity.employee.Employee;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Stateless
public class EmployeeRepository extends AbstractRepository<Employee> {


  public Optional<Employee> findByLogin(String login) {
    final String SELECT_BY_LOGIN = "SELECT e FROM Employee e WHERE e.login = :login";
    TypedQuery<Employee> query = entityManager.createQuery(SELECT_BY_LOGIN, Employee.class);
    query.setParameter("login", login);
    return query.getResultStream().findFirst();
  }

  public List<Employee> findAll() {
    final String SELECT_ALL = "SELECT e FROM Employee e";
    return entityManager.createQuery(SELECT_ALL, Employee.class).getResultList();
  }

  @Override
  public Class<Employee> getEntityClass() {
    return Employee.class;
  }

}
