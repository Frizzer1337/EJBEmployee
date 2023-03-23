package com.frizzer.employeapp.test;

import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.restassured.spi.AuthFilter;

public class JwtAuthFilter implements AuthFilter {

  String token;

  public JwtAuthFilter(String token) {
    this.token = token;
  }

  @Override
  public Response filter(FilterableRequestSpecification filterableRequestSpecification,
      FilterableResponseSpecification filterableResponseSpecification,
      FilterContext filterContext) {
    filterableRequestSpecification.replaceHeader("Authorization", "Bearer " + token);
    return filterContext.next(filterableRequestSpecification, filterableResponseSpecification);
  }
}
