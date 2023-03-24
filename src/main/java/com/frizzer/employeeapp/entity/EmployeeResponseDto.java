package com.frizzer.employeeapp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeResponseDto {

  @JsonProperty("id")
  private long id;
  @NotNull(message = "Login cannot be null")
  @Size(min = 5, max = 45, message = "Login must be between 5 and 45 characters")
  private String login;
  @NotNull(message = "Role cannot be null")
  @Enumerated(EnumType.STRING)
  private EmployeeRole role;

}
