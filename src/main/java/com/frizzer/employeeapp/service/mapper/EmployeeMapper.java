package com.frizzer.employeeapp.service.mapper;

import com.frizzer.employeeapp.entity.Employee;
import com.frizzer.employeeapp.entity.EmployeeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {

  EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

  @Mapping(source = "login", target = "login")
  @Mapping(source = "password", target = "password")
  EmployeeDto toDto(Employee employee);

  @Mapping(source = "login", target = "login")
  @Mapping(source = "password", target = "password")
  Employee fromDto(EmployeeDto employeeDto);

}
