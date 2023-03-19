package com.frizzer.employeeapp.controller.resource;

import com.frizzer.employeeapp.entity.EmployeeDto;
import com.frizzer.employeeapp.entity.EmployeeMapper;
import com.frizzer.employeeapp.service.EmployeeService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/employees")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeResource {

  @EJB
  private EmployeeService employeeService;

  @POST
  public Response save(EmployeeDto employee) {
    return Response
        .ok(employeeService.save(EmployeeMapper.INSTANCE.fromDto(employee)))
        .build();
  }

  @PUT
  @Path("/{id}")
  public Response update(@PathParam("id") Long id, EmployeeDto employee) {
    return Response
        .ok(employeeService.update(EmployeeMapper.INSTANCE.fromDto(employee), id))
        .build();
  }

  @GET
  @Path("/{id}")
  public Response findById(@PathParam("id") Long id) {
    return Response.ok()
        .entity(EmployeeMapper.INSTANCE.toDto(employeeService.findById(id)))
        .build();
  }

  @DELETE
  @Path("/{id}")
  public Response delete(@PathParam("id") Long id) {
    boolean entityExists = employeeService.delete(id);
    return entityExists ? Response.ok(Status.OK).build()
        : Response.status(Status.NOT_FOUND).build();
  }

  @GET
  @Path("/hello")
  public String hello() {
    return "Hello,i'm currently live";
  }

}