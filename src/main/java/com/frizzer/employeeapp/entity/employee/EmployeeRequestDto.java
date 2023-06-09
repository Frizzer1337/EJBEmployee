package com.frizzer.employeeapp.entity.employee;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeRequestDto {

  @JsonProperty("login")
  @NotNull(message = "Login cannot be null")
  @Size(min = 5, max = 45, message = "Login must be between 5 and 45 characters")
  private String login;
  @NotNull(message = "Password cannot be null")
  @Size(min = 5, max = 255, message = "Password must be between 5 and 255 characters")
  private String password;

}
