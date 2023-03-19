package com.frizzer.employeeapp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class EmployeeInfoDto {

  @JsonProperty("name")
  private String name;
  private String surname;
  private BigDecimal salary;
}
