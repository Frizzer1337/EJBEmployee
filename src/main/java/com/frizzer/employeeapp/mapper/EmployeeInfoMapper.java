package com.frizzer.employeeapp.mapper;

import com.frizzer.employeeapp.entity.EmployeeInfo;
import com.frizzer.employeeapp.entity.EmployeeInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeInfoMapper {

  EmployeeInfoMapper INSTANCE = Mappers.getMapper(EmployeeInfoMapper.class);

  @Mapping(source = "name", target = "name")
  @Mapping(source = "surname", target = "surname")
  @Mapping(source = "salary", target = "salary")
  EmployeeInfoDto toDto(EmployeeInfo employeeInfo);

  @Mapping(source = "name", target = "name")
  @Mapping(source = "surname", target = "surname")
  @Mapping(source = "salary", target = "salary")
  EmployeeInfo fromDto(EmployeeInfoDto employeeInfoDto);

}
