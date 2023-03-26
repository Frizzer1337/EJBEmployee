package com.frizzer.employeeapp.exception;

import jakarta.persistence.PersistenceException;

public class DataNotFoundException extends PersistenceException {

  public DataNotFoundException(String message) {
    super(message);
  }

  public DataNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

}
