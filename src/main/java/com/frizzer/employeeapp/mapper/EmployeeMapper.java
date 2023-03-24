package com.frizzer.employeeapp.mapper;

import com.frizzer.employeeapp.entity.Employee;
import com.frizzer.employeeapp.entity.EmployeeRequestDto;
import com.frizzer.employeeapp.entity.EmployeeResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {

  EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

  @Mapping(source = "login", target = "login")
  @Mapping(source = "password", target = "password")
  EmployeeRequestDto toRequestDto(Employee employee);

  @Mapping(source = "login", target = "login")
  @Mapping(source = "password", target = "password")
  Employee fromRequestDto(EmployeeRequestDto employeeRequestDto);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "login", target = "login")
  @Mapping(source = "role", target = "role")
  EmployeeResponseDto toResponseDto(Employee employee);
  @Mapping(source = "id", target = "id")
  @Mapping(source = "login", target = "login")
  @Mapping(source = "role", target = "role")
  Employee fromResponseDto(EmployeeResponseDto employeeResponseDto);

}
