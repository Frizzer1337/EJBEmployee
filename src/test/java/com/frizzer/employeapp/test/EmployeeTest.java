package com.frizzer.employeapp.test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import com.frizzer.employeeapp.entity.employee.Employee;
import com.frizzer.employeeapp.entity.employee.EmployeeRequestDto;
import com.google.gson.JsonObject;
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
  private final String ADMIN_LOGIN = "pablomogila";
  private final String ADMIN_PASSWORD = "23415";
  private final String AUTHORIZATION = "authorization";
  private final String BAD_AUTHORIZATION = "bad_auth";

  @SneakyThrows
  public String getAccessToken(String login, String password) {
    String LOGIN = "/login";
    JsonObject admin = new JsonObject();
    admin.addProperty("login", login);
    admin.addProperty("password", password);
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(BASE_URL + LOGIN))
        .header("Content-Type", JSON)
        .POST(HttpRequest.BodyPublishers.ofString(
            "{\n\t\"login\":\"" + login + "\",\n\t\"password\":\"" + password + "\"\n}"))
        .build();
    HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    return response.headers().firstValue(AUTHORIZATION).orElse(BAD_AUTHORIZATION);

  }

  @Test
  void testFindById() {
    String accessToken = getAccessToken(ADMIN_LOGIN, ADMIN_PASSWORD).split(" ")[1];
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
    String accessToken = getAccessToken(ADMIN_LOGIN, ADMIN_PASSWORD).split(" ")[1];
    String fakeShortLogin = RandomStringUtils.random(4);
    String fakePassword = RandomStringUtils.random(5);
    EmployeeRequestDto badEmployee = new EmployeeRequestDto();
    badEmployee.setLogin(fakeShortLogin);
    badEmployee.setPassword(fakePassword);
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
    String accessToken = getAccessToken(ADMIN_LOGIN, ADMIN_PASSWORD).split(" ")[1];
    String fakeShortPassword = RandomStringUtils.random(4);
    String fakeLogin = RandomStringUtils.random(5);
    EmployeeRequestDto badEmployee = new EmployeeRequestDto();
    badEmployee.setLogin(fakeShortPassword);
    badEmployee.setPassword(fakeLogin);
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
    String accessToken = getAccessToken(ADMIN_LOGIN, ADMIN_PASSWORD).split(" ")[1];
    String fakeLogin = RandomStringUtils.random(10);
    String fakePassword = RandomStringUtils.random(10);
    EmployeeRequestDto badEmployee = new EmployeeRequestDto();
    badEmployee.setLogin(fakeLogin);
    badEmployee.setPassword(fakePassword);
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

  @Test
  void testUpdateByFakeData() {
    String accessToken = getAccessToken(ADMIN_LOGIN, ADMIN_PASSWORD).split(" ")[1];
    long id = 15;
    String fakeLogin = RandomStringUtils.random(10);
    String fakePassword = RandomStringUtils.random(10);
    EmployeeRequestDto badEmployee = new EmployeeRequestDto();
    badEmployee.setLogin(fakeLogin);
    badEmployee.setPassword(fakePassword);
    JwtAuthFilter authFilter = new JwtAuthFilter(accessToken);
    given().baseUri(BASE_URL)
        .contentType(JSON)
        .body(badEmployee)
        .filter(authFilter)
        .when()
        .put(BASE_URL + "/" + 15)
        .then()
        .assertThat()
        .statusCode(200);
  }


}
