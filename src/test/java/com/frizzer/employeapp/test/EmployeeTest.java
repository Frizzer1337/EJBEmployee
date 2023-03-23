package com.frizzer.employeapp.test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import com.frizzer.employeeapp.entity.Employee;
import com.frizzer.employeeapp.entity.EmployeeDto;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

class EmployeeTest {

  private final String BASE_URL = "http://localhost:6005/EmployeeApp-1.0-SNAPSHOT/api/employees";
  private final String JSON = "application/json";

  @SneakyThrows
  public String getAccessToken(String login, String password) {
    String LOGIN = "/login";
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(BASE_URL + LOGIN))
        .header("Content-Type", JSON)
        .method("POST", HttpRequest.BodyPublishers.ofString(
            "{\n\t\"login\":\"" + login + "\",\n\t\"password\":\"" + password + "\"\n}"))
        .build();
    HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    return response.headers().firstValue("authorization").orElse("Wrong data");

  }

  @Test
  void testFindById() {
    String accessToken = getAccessToken("pablomogila", "23415").split(" ")[1];
    JwtAuthFilter authFilter = new JwtAuthFilter(accessToken);
    long id = 15;
    Employee employee =
        given().baseUri(BASE_URL)
            .contentType(JSON)
            .filter(authFilter)
            .when()
            .get(BASE_URL + "/" + id)
            .then()
            .assertThat()
            .statusCode(200)
            .and()
            .extract()
            .as(Employee.class);
    assertThat(employee.getLogin(), is(notNullValue()));
  }

  @Test
  void testSaveByShortLogin() {
    String accessToken = getAccessToken("pablomogila", "23415").split(" ")[1];
    String fakeShortLogin = RandomStringUtils.random(4);
    EmployeeDto badEmployee = EmployeeDto.builder().login(fakeShortLogin).password("23242").build();
    JwtAuthFilter authFilter = new JwtAuthFilter(accessToken);
    given().baseUri(BASE_URL)
        .contentType(JSON)
        .body(badEmployee)
        .filter(authFilter)
        .when()
        .post(BASE_URL)
        .then()
        .assertThat()
        .statusCode(400);
  }

  @Test
  void testSaveByShortPassword() {
    String accessToken = getAccessToken("pablomogila", "23415").split(" ")[1];
    String fakeShortPassword = RandomStringUtils.random(4);
    EmployeeDto badEmployee = EmployeeDto.builder().login("pabloo").password(fakeShortPassword).build();
    JwtAuthFilter authFilter = new JwtAuthFilter(accessToken);
    given().baseUri(BASE_URL)
        .contentType(JSON)
        .body(badEmployee)
        .filter(authFilter)
        .when()
        .post(BASE_URL)
        .then()
        .assertThat()
        .statusCode(400);
  }

  @Test
  void testSaveByFakeData() {
    String accessToken = getAccessToken("pablomogila", "23415").split(" ")[1];
    String fakeLogin = RandomStringUtils.random(10);
    String fakePassword = RandomStringUtils.random(10);
    EmployeeDto badEmployee = EmployeeDto.builder().login(fakeLogin).password(fakePassword).build();
    JwtAuthFilter authFilter = new JwtAuthFilter(accessToken);
    given().baseUri(BASE_URL)
        .contentType(JSON)
        .body(badEmployee)
        .filter(authFilter)
        .when()
        .post(BASE_URL)
        .then()
        .assertThat()
        .statusCode(200);
  }


}
