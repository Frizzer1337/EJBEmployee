package com.frizzer.employeeapp.controller;

import com.frizzer.employeeapp.controller.resource.EmployeeResource;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.Set;

@ApplicationPath("/api")
public class EmployeeApplication extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> resources = new java.util.HashSet<>();
    addRestResourceClasses(resources);
    return resources;
  }

  public void addRestResourceClasses(Set<Class<?>> resources) {
    resources.add(EmployeeResource.class);
  }

}