package com.frizzer.employeeapp.controller.resource;

import com.frizzer.employeeapp.entity.Employee;
import com.frizzer.employeeapp.entity.EmployeeDto;
import com.frizzer.employeeapp.mapper.EmployeeMapper;
import com.frizzer.employeeapp.security.JwtTokenService;
import com.frizzer.employeeapp.service.EmployeeService;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/employees")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@DeclareRoles({"ADMIN", "WORKER"})
public class EmployeeResource {

  @EJB
  private EmployeeService employeeService;
  @EJB
  private JwtTokenService jwtTokenService;

  @POST
  @RolesAllowed("ADMIN")
  public Response save(@Valid EmployeeDto employee) {
    return Response.ok(employeeService.save(EmployeeMapper.INSTANCE.fromDto(employee))).build();
  }

  @POST
  @Path(("/login"))
  @PermitAll
  public Response login(@Valid EmployeeDto employee) {
    Employee entity = employeeService.findByLogin(employee.getLogin());
    if (entity != null && entity.getPassword().equals(employee.getPassword())) {
      String token = jwtTokenService.generateToken(entity.getLogin(), entity.getRole());
      return Response.ok("You're successfully logged in").header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
    } else {
      return Response.status(Status.UNAUTHORIZED).build();
    }
  }

  @PUT
  @Path("/{id}")
  @RolesAllowed("ADMIN")
  public Response update(@PathParam("id") Long id, @Valid EmployeeDto employee) {
    return Response.ok(employeeService.update(EmployeeMapper.INSTANCE.fromDto(employee), id)).build();

  }

  @GET
  @Path("/{id}")
  @RolesAllowed({"ADMIN", "WORKER"})
  public Response findById(@PathParam("id") Long id) {
    return Response.ok().entity(EmployeeMapper.INSTANCE.toDto(employeeService.findById(id))).build();
  }

  @DELETE
  @Path("/{id}")
  @RolesAllowed("ADMIN")
  public Response delete(@PathParam("id") Long id) {
    boolean entityExists = employeeService.delete(id);
    return entityExists ? Response.ok(Status.OK).build() : Response.status(Status.NOT_FOUND).build();
  }

  @GET
  @Path("/admin")
  @RolesAllowed("ADMIN")
  public Response hiAdmin() {
    return Response.ok("Hi admin").build();
  }

  @GET
  @Path("/worker")
  @RolesAllowed({"WORKER"})
  public Response hiWorker() {
    return Response.ok("Hi worker").build();
  }


}
