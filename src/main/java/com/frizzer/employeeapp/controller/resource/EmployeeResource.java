package com.frizzer.employeeapp.controller.resource;

import com.frizzer.employeeapp.entity.employee.EmployeeRequestDto;
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


  @POST
  @RolesAllowed("ADMIN")
  public Response save(@Valid EmployeeRequestDto employee) {
    if (employeeService.checkIfLoginExists(employee.getLogin())) {
      return Response.status(Status.CONFLICT).entity("Login " + employee.getLogin() + "is already taken").build();
    }
    return Response.ok(employeeService.save(employee)).build();
  }

  @POST
  @Path(("/login"))
  @PermitAll
  public Response login(@Valid EmployeeRequestDto employee) {
    if (employeeService.checkIfPasswordCorrect(employee)) {
      String token = employeeService.generateToken(employee);
      return Response.ok("You're successfully logged in").header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
    } else {
      return Response.status(Status.UNAUTHORIZED).build();
    }
  }

  @PUT
  @Path("/{id}")
  @RolesAllowed("ADMIN")
  public Response update(@PathParam("id") Long id, @Valid EmployeeRequestDto employee) {
    if(!employeeService.checkIfUserExists(id)){
      return Response.status(Status.NOT_FOUND).entity("User with id " + id + " doesn't exist").build();
    }
    if(employeeService.checkIfLoginExists(employee.getLogin())){
      return Response.status(Status.CONFLICT).entity("Login " + employee.getLogin() + "is already taken").build();
    }
    return Response.ok(employeeService.update(employee, id)).build();

  }

  @GET
  @Path("")
  @RolesAllowed({"ADMIN", "WORKER"})
  public Response findAll() {
    return Response.ok().entity(employeeService.findAll()).build();
  }

  @GET
  @Path("/{id}")
  @RolesAllowed({"ADMIN", "WORKER"})
  public Response findById(@PathParam("id") Long id) {
    return Response.ok().entity(employeeService.findById(id)).build();
  }

  @DELETE
  @Path("/{id}")
  @RolesAllowed("ADMIN")
  public Response delete(@PathParam("id") Long id) {
    if(!employeeService.checkIfUserExists(id)){
      return Response.status(Status.NOT_FOUND).entity("User with id " + id + " doesn't exist").build();
    }
    employeeService.delete(id);
    return Response.ok().build();
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
