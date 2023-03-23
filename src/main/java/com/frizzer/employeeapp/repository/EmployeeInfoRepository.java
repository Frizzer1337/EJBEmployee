package com.frizzer.employeeapp.repository;

import com.frizzer.employeeapp.entity.EmployeeInfo;
import jakarta.ejb.Stateless;

@Stateless
public class EmployeeInfoRepository extends AbstractRepository<EmployeeInfo> {

  @Override
  public Class<EmployeeInfo> getEntityClass() {
    return EmployeeInfo.class;
  }
}
