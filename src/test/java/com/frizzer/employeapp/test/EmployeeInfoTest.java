package com.frizzer.employeapp.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.frizzer.employeeapp.entity.employeeinfo.EmployeeInfo;
import com.frizzer.employeeapp.entity.employeeinfo.EmployeeInfoDto;
import com.frizzer.employeeapp.repository.EmployeeInfoRepository;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class EmployeeInfoTest extends AbstractTest {

  private final String BASE_URL = "http://localhost:6005/EmployeeApp-1.0-SNAPSHOT/api/employees-info";

  @Mock
  private static EmployeeInfoRepository employeeInfoRepository;

  @BeforeAll
  public static void init() {
    employeeInfoRepository = mock(EmployeeInfoRepository.class);
    when(employeeInfoRepository.findAll()).thenReturn(
        List.of(new EmployeeInfo(113L, "Name", "Surname", BigDecimal.valueOf(new SecureRandom().nextDouble()))));
  }

  @Test
  void testFindById() {
    String accessToken = getAccessToken(ADMIN_LOGIN, ADMIN_PASSWORD).split(" ")[1];
    JwtAuthFilter authFilter = new JwtAuthFilter(accessToken);
    long id = employeeInfoRepository.findAll().get(0).getId();
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
    long id = employeeInfoRepository.findAll().get(0).getId();
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
    long id = employeeInfoRepository.findAll().get(0).getId();
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
    long id = employeeInfoRepository.findAll().get(0).getId();
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
