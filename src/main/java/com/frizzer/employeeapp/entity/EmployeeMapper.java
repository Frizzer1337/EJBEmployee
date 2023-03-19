package com.frizzer.employeeapp.entity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {

  EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);
  @Mapping(source = "name", target = "name")
  @Mapping(source = "surname", target = "surname")
  @Mapping(source = "role", target = "role")
  @Mapping(source = "salary", target = "salary")
  EmployeeDto toDto(Employee employee);

  @Mapping(source = "name", target = "name")
  @Mapping(source = "surname", target = "surname")
  @Mapping(source = "role", target = "role")
  @Mapping(source = "salary", target = "salary")
  Employee fromDto(EmployeeDto employeeDto);

}
