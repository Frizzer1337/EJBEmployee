package com.frizzer.employeeapp.controller;

import com.frizzer.employeeapp.controller.resource.EmployeeInfoResource;
import com.frizzer.employeeapp.controller.resource.EmployeeResource;
import com.frizzer.employeeapp.controller.resource.JwtTokenFilter;
import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.Set;

@ApplicationPath("/api")
@DeclareRoles({"ADMIN", "WORKER"})
public class EmployeeApplication extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> resources = new java.util.HashSet<>();
    addRestResourceClasses(resources);
    return resources;
  }

  public void addRestResourceClasses(Set<Class<?>> resources) {
    resources.add(EmployeeResource.class);
    resources.add(EmployeeInfoResource.class);
    resources.add(JwtTokenFilter.class);
  }

}