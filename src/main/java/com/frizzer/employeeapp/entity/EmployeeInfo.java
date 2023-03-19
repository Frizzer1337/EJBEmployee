package com.frizzer.employeeapp.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "employee", schema = "employee_task")
public class EmployeeInfo {

  @Id
  private Long id;
  private String name;
  private String surname;
  private BigDecimal salary;
}
