package com.frizzer.employeeapp.exception;

import jakarta.ws.rs.WebApplicationException;

public class EmployeeApplicationException extends WebApplicationException {

  public EmployeeApplicationException(String message) {
    super(message);
  }

  public EmployeeApplicationException(String message, Throwable cause) {
    super(message, cause);
  }

}
