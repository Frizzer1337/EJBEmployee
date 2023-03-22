package com.frizzer.employeeapp.controller.resource.secure;

import com.frizzer.employeeapp.security.JwtSecurityContext;
import com.frizzer.employeeapp.security.JwtTokenService;
import jakarta.annotation.Priority;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class TokenAuthenticationFilter implements ContainerRequestFilter {

  private static final String REALM = "example";
  private static final String AUTHENTICATION_SCHEME = "Bearer";
  private static final Set<String> EXCLUDED_PATHS = new HashSet<>(List.of("employees/login"));
  @Context
  private UriInfo uriInfo;

  @EJB
  private JwtTokenService jwtTokenService;

  @Override
  public void filter(ContainerRequestContext requestContext) {
    if (EXCLUDED_PATHS.contains(uriInfo.getPath())) {
      return;
    }

    String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

    if (authorizationHeader == null || !authorizationHeader.startsWith(AUTHENTICATION_SCHEME)) {
      abortWithUnauthorized(requestContext);
      return;
    }

    String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();

    try {
      String principal = jwtTokenService.parseToken(token).getBody().getSubject();
      String role = jwtTokenService.parseToken(token).getBody().get("role", String.class);
      requestContext.setSecurityContext(
          new JwtSecurityContext(principal, requestContext.getSecurityContext().isSecure(), role));
    } catch (Exception e) {
      abortWithUnauthorized(requestContext);
    }
  }

  private void abortWithUnauthorized(ContainerRequestContext requestContext) {
    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
        .header(HttpHeaders.WWW_AUTHENTICATE, AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"")
        .build());
  }

}