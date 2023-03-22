package com.frizzer.employeeapp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeDto {

  @JsonProperty("login")
  @NotNull(message = "Login cannot be null")
  @Size(min = 5, max = 45, message = "Login must be between 5 and 45 characters")
  private String login;
  @NotNull(message = "Password cannot be null")
  @Size(min = 5, max = 255, message = "Password must be between 5 and 255 characters")
  private String password;

}
