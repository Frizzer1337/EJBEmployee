package com.frizzer.employeeapp.entity.employeeinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class EmployeeInfoDto {

  @JsonProperty("name")
  @NotNull(message = "Name cannot be null")
  @Size(min = 5, max = 45, message = "Login must be between 5 and 45 characters")
  private String name;
  @NotNull(message = "Surname cannot be null")
  @Size(min = 5, max = 45, message = "Login must be between 5 and 45 characters")
  private String surname;
  @NotNull(message = "Salary cannot be null")
  private BigDecimal salary;
}
