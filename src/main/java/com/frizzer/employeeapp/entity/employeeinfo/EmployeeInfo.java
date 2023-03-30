package com.frizzer.employeeapp.entity.employeeinfo;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "employee_info", schema = "employee_task")
public class EmployeeInfo {

  @Id
  @NonNull
  private Long id;
  @NonNull
  private String name;
  @NonNull
  private String surname;
  @NonNull
  private BigDecimal salary;
}
