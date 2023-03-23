package com.frizzer.employeapp.test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import com.frizzer.employeeapp.entity.Employee;
import com.frizzer.employeeapp.entity.EmployeeDto;
import io.restassured.specification.RequestSpecification;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

class EmployeeTest {

  @SneakyThrows
  public String getAccessToken(String login, String password) {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:6005/EmployeeApp-1.0-SNAPSHOT/api/employees/login"))
        .header("Content-Type", "application/json").method("POST",
            HttpRequest.BodyPublishers.ofString(
                "{\n\t\"login\":\"" + login + "\",\n\t\"password\":\"" + password + "\"\n}"))
        .build();
    HttpResponse<String> response = HttpClient.newHttpClient()
        .send(request, HttpResponse.BodyHandlers.ofString());
    return response.headers().firstValue("authorization").orElse("Wrong data");

  }

  @Test
  void testFindById() {
    String accessToken = getAccessToken("pablomogila", "23415").split(" ")[1];
    JwtAuthFilter authFilter = new JwtAuthFilter(accessToken);
    RequestSpecification requestSpecification = given().baseUri(
            "http://localhost:6005/EmployeeApp-1.0-SNAPSHOT/api/employees")
        .contentType("application/json").filter(authFilter);
    Long id = 15l;
    Employee employee = requestSpecification.when()
        .get("http://localhost:6005/EmployeeApp-1.0-SNAPSHOT/api/employees/" + id).then()
        .assertThat().statusCode(200).and().extract().as(Employee.class);
    System.out.println(employee);
    assertThat(employee.getLogin(), is(notNullValue()));
  }

  @Test
  void testSaveByShortLogin() {
    String accessToken = getAccessToken("pablomogila", "23415").split(" ")[1];
    EmployeeDto badEmployee = EmployeeDto.builder().login("pab").password("23242").build();
    JwtAuthFilter authFilter = new JwtAuthFilter(accessToken);
    RequestSpecification requestSpecification = given().baseUri(
            "http://localhost:6005/EmployeeApp-1.0-SNAPSHOT/api/employees")
        .contentType("application/json").body(badEmployee).filter(authFilter);
    requestSpecification.when()
        .post("http://localhost:6005/EmployeeApp-1.0-SNAPSHOT/api/employees/").then().assertThat()
        .statusCode(400);
  }


}
