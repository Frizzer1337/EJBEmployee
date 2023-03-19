package com.frizzer.employeeapp.controller.resource;

import com.frizzer.employeeapp.entity.EmployeeInfoDto;
import com.frizzer.employeeapp.service.EmployeeInfoService;
import com.frizzer.employeeapp.service.mapper.EmployeeInfoMapper;
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

@Path("/employees-info")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeInfoResource {

  @EJB
  private EmployeeInfoService employeeInfoService;

  @POST
  @Path("/{id}")
  public Response save(@PathParam("id") Long id, EmployeeInfoDto employee) {
    return Response
        .ok(employeeInfoService.save(EmployeeInfoMapper.INSTANCE.fromDto(employee), id))
        .build();
  }

  @PUT
  @Path("/{id}")
  public Response update(@PathParam("id") Long id, EmployeeInfoDto employee) {
    return Response
        .ok(employeeInfoService.update(EmployeeInfoMapper.INSTANCE.fromDto(employee), id))
        .build();
  }

  @GET
  @Path("/{id}")
  public Response findById(@PathParam("id") Long id) {
    return Response.ok()
        .entity(EmployeeInfoMapper.INSTANCE.toDto(employeeInfoService.findById(id)))
        .build();
  }

  @DELETE
  @Path("/{id}")
  public Response delete(@PathParam("id") Long id) {
    boolean entityExists = employeeInfoService.delete(id);
    return entityExists ? Response.ok(Status.OK).build()
        : Response.status(Status.NOT_FOUND).build();
  }

  @GET
  @Path("/hello")
  public String hello() {
    return "Hello,i'm currently live";
  }

}