package com.frizzer.employeeapp.mapper;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {
  @Override
  public Response toResponse(WebApplicationException exception) {
    return Response.status(Status.BAD_REQUEST)
        .entity(exception.getMessage())
        .build();
  }

}
