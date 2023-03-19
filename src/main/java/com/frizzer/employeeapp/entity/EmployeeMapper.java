package com.frizzer.employeeapp.entity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {

  EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

  EmployeeDto toDto(Employee employee);
  @Mapping(target = "id", ignore = true)
  Employee fromDto(EmployeeDto employeeDto);

}
