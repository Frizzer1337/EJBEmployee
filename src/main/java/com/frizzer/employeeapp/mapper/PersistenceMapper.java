package com.frizzer.employeeapp.mapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class PersistenceMapper implements ExceptionMapper<RuntimeException> {

  @Override
  public Response toResponse(RuntimeException exception) {
    return Response.status(Status.BAD_REQUEST)
        .entity(exception.getMessage())
        .build();
  }

}
