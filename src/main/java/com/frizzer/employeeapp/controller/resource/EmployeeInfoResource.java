package com.frizzer.employeeapp.controller.resource;

import com.frizzer.employeeapp.entity.employeeinfo.EmployeeInfoDto;
import com.frizzer.employeeapp.service.EmployeeInfoService;
import jakarta.annotation.security.DeclareRoles;
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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/employees-info")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@DeclareRoles({"ADMIN", "WORKER"})
public class EmployeeInfoResource {

  @EJB
  private EmployeeInfoService employeeInfoService;

  @POST
  @Path("/{id}")
  @RolesAllowed("ADMIN")
  public Response save(@PathParam("id") Long id, @Valid EmployeeInfoDto employee) {
    if (!employeeInfoService.checkIfEmployeeInfoExists(id)) {
      return Response.status(Status.NOT_FOUND).entity("Employee info with id " + id + " does not exist").build();
    }
    return Response.ok(employeeInfoService.save(employee, id)).build();

  }

  @PUT
  @Path("/{id}")
  @RolesAllowed("ADMIN")
  public Response update(@PathParam("id") Long id, @Valid EmployeeInfoDto employee) {
    if (!employeeInfoService.checkIfEmployeeInfoExists(id)) {
      return Response.status(Status.NOT_FOUND).entity("Employee info with id " + id + " does not exist").build();
    }
    return Response.ok(employeeInfoService.update(employee, id)).build();

  }

  @GET
  @Path("/{id}")
  public Response findById(@PathParam("id") Long id) {
    return Response.ok().entity(employeeInfoService.findById(id)).build();
  }

  @GET
  @Path("")
  public Response findAll(){
    return Response.ok().entity(employeeInfoService.findAll()).build();
  }

  @DELETE
  @Path("/{id}")
  @RolesAllowed("ADMIN")
  public Response delete(@PathParam("id") Long id) {
    if (!employeeInfoService.checkIfEmployeeInfoExists(id)) {
      return Response.status(Status.NOT_FOUND).entity("Employee info with id " + id + " does not exist").build();
    }
    employeeInfoService.delete(id);
    return Response.ok(Status.OK).build();
  }


}