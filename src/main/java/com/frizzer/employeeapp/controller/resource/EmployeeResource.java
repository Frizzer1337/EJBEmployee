package com.frizzer.employeeapp.controller.resource;

import com.frizzer.employeeapp.entity.EmployeeDto;
import com.frizzer.employeeapp.entity.EmployeeMapper;
import com.frizzer.employeeapp.service.EmployeeService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/employees")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeResource {

  @EJB
  private EmployeeService employeeService;

  @POST
  public Response save(EmployeeDto employee) {
    return Response.ok(employeeService.save(EmployeeMapper.INSTANCE.fromDto(employee))).build();
  }

  @GET
  @Path("/{id}")
  public Response findAll(@PathParam("id") Long id) {
    return Response.ok().entity(EmployeeMapper.INSTANCE.toDto(employeeService.findById(id))).build();
  }

  @GET
  @Path("/hello")
  public String hello(){
    return "Hello,i'm currently live";
  }

}