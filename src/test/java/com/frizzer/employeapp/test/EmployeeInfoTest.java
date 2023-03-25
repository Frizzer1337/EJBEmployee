package com.frizzer.employeapp.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import com.frizzer.employeeapp.entity.employeeinfo.EmployeeInfo;
import com.frizzer.employeeapp.entity.employeeinfo.EmployeeInfoDto;
import java.math.BigDecimal;
import java.security.SecureRandom;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

class EmployeeInfoTest extends AbstractTest {

  private final String BASE_URL = "http://localhost:6005/EmployeeApp-1.0-SNAPSHOT/api/employees-info";

  @Test
  void testFindById() {
    String accessToken = getAccessToken(ADMIN_LOGIN, ADMIN_PASSWORD).split(" ")[1];
    JwtAuthFilter authFilter = new JwtAuthFilter(accessToken);
    long id = 15;
    EmployeeInfo employee =
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
            .as(EmployeeInfo.class);
    assertThat(employee.getName(), is(notNullValue()));
  }

  @Test
  void testSaveByShortName() {
    String accessToken = getAccessToken(ADMIN_LOGIN, ADMIN_PASSWORD).split(" ")[1];
    String fakeShortName = RandomStringUtils.random(4);
    String fakeSurname = RandomStringUtils.random(5);
    BigDecimal fakeSalary = BigDecimal.valueOf(new SecureRandom().nextDouble());
    EmployeeInfoDto badEmployee = new EmployeeInfoDto();
    long id = 15;
    badEmployee.setName(fakeShortName);
    badEmployee.setSurname(fakeSurname);
    badEmployee.setSalary(fakeSalary);
    JwtAuthFilter authFilter = new JwtAuthFilter(accessToken);
    given().baseUri(BASE_URL)
        .contentType(JSON)
        .body(badEmployee)
        .filter(authFilter)
        .when()
        .post(BASE_URL + "/" + id)
        .then()
        .assertThat()
        .statusCode(400);
  }

  @Test
  void testSaveByShortSurname() {
    String accessToken = getAccessToken(ADMIN_LOGIN, ADMIN_PASSWORD).split(" ")[1];
    long id = 15;
    String fakeName = RandomStringUtils.random(5);
    String fakeShortSurname = RandomStringUtils.random(4);
    BigDecimal fakeSalary = BigDecimal.valueOf(new SecureRandom().nextDouble());
    EmployeeInfoDto badEmployee = new EmployeeInfoDto();
    badEmployee.setName(fakeName);
    badEmployee.setSurname(fakeShortSurname);
    badEmployee.setSalary(fakeSalary);
    JwtAuthFilter authFilter = new JwtAuthFilter(accessToken);
    given().baseUri(BASE_URL)
        .contentType(JSON)
        .body(badEmployee)
        .filter(authFilter)
        .when()
        .post(BASE_URL + "/" + id)
        .then()
        .assertThat()
        .statusCode(400);
  }

  @Test
  void testSaveByFakeData() {
    String accessToken = getAccessToken(ADMIN_LOGIN, ADMIN_PASSWORD).split(" ")[1];
    String fakeShortName = RandomStringUtils.random(10);
    String fakeSurname = RandomStringUtils.random(10);
    long id = 15;
    BigDecimal fakeSalary = BigDecimal.valueOf(new SecureRandom().nextDouble());
    EmployeeInfoDto badEmployee = new EmployeeInfoDto();
    badEmployee.setName(fakeShortName);
    badEmployee.setSurname(fakeSurname);
    badEmployee.setSalary(fakeSalary);
    JwtAuthFilter authFilter = new JwtAuthFilter(accessToken);
    given().baseUri(BASE_URL)
        .contentType(JSON)
        .body(badEmployee)
        .filter(authFilter)
        .when()
        .post(BASE_URL + "/" + id)
        .then()
        .assertThat()
        .statusCode(500);
  }

}
