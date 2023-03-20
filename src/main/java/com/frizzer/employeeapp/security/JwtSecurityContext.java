package com.frizzer.employeeapp.security;

import com.frizzer.employeeapp.entity.EmployeeRole;
import jakarta.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JwtSecurityContext implements SecurityContext {

  private final String username;
  private final boolean isSecure;
  private final List<String> roles;

  public JwtSecurityContext(String username, boolean isSecure, String... roles) {
    this.username = username;
    this.isSecure = isSecure;
    this.roles = Collections.unmodifiableList(Arrays.asList(roles));
  }

  @Override
  public Principal getUserPrincipal() {
    return () -> username;
  }
  @Override
  public boolean isUserInRole(String role) {
    return roles.contains(role);
  }
  @Override
  public boolean isSecure() {
    return isSecure;
  }
  @Override
  public String getAuthenticationScheme() {
    return "Bearer";
  }

  public boolean isUserInRole(EmployeeRole employeeRole){
    return isUserInRole(employeeRole.toString());
  }


}
