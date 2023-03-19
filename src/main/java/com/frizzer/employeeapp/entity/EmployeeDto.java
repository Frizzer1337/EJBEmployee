package com.frizzer.employeeapp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EmployeeDto {

  @JsonProperty("login")
  private String login;
  private String password;

}
