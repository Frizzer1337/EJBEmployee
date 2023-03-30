package com.frizzer.employeeapp.repository;

import com.frizzer.employeeapp.entity.employeeinfo.EmployeeInfo;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class EmployeeInfoRepository extends AbstractRepository<EmployeeInfo> {

  public List<EmployeeInfo> findAll() {
    final String SELECT_ALL = "SELECT e FROM EmployeeInfo e";
    return entityManager.createQuery(SELECT_ALL, EmployeeInfo.class).getResultList();
  }

  @Override
  public Class<EmployeeInfo> getEntityClass() {
    return EmployeeInfo.class;
  }
}
