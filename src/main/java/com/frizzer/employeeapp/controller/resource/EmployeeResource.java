package com.frizzer.employeeapp.controller.resource;

import com.frizzer.employeeapp.entity.Employee;
import com.frizzer.employeeapp.entity.EmployeeDto;
import com.frizzer.employeeapp.entity.EmployeeRole;
import com.frizzer.employeeapp.security.JwtSecurityContext;
import com.frizzer.employeeapp.security.JwtTokenService;
import com.frizzer.employeeapp.service.EmployeeService;
import com.frizzer.employeeapp.service.mapper.EmployeeMapper;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/employees")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed({"ADMIN", "WORKER"})
public class EmployeeResource {

  @EJB
  private EmployeeService employeeService;
  @EJB
  private JwtTokenService jwtTokenService;

  @POST
  public Response save(EmployeeDto employee) {
    return Response
        .ok(employeeService.save(EmployeeMapper.INSTANCE.fromDto(employee)))
        .build();
  }

  @POST
  @PermitAll
  @Path(("/login"))
  public Response login(EmployeeDto employee) {
    Employee entity = employeeService.findByLogin(employee.getLogin());
    if (entity != null && entity.getPassword().equals(employee.getPassword())) {
      String token = jwtTokenService.generateToken(entity.getLogin(), entity.getRole());
      return Response.ok()
          .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
          .build();
    } else {
      return Response.status(Status.UNAUTHORIZED).build();
    }
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
  @Path("/admin")
  @RolesAllowed("ADMIN")
  public Response hiAdmin(@Context SecurityContext context) {
    JwtSecurityContext jwtContext = (JwtSecurityContext) context;
    if (!jwtContext.isUserInRole(EmployeeRole.ADMIN)) {
      return Response.status(Response.Status.FORBIDDEN).build();
    }
    return Response.ok("Hi admin!").build();
  }

  @GET
  @Path("/worker")
  @RolesAllowed({"WORKER"})
  public Response hiWorker(@Context SecurityContext context) {
    JwtSecurityContext jwtContext = (JwtSecurityContext) context;
    if (!jwtContext.isUserInRole(EmployeeRole.WORKER)) {
      return Response.status(Response.Status.FORBIDDEN).build();
    }
    return Response.ok("Hi worker!").build();
  }

}
