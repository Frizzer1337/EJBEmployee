package com.frizzer.employeapp.test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.frizzer.employeeapp.entity.employee.Employee;
import com.frizzer.employeeapp.entity.employee.EmployeeRequestDto;
import com.frizzer.employeeapp.repository.EmployeeRepository;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;


class EmployeeTest extends AbstractTest {

  @Mock
  private static EmployeeRepository employeeRepository;

  @BeforeAll
  public static void init() {
    employeeRepository = mock(EmployeeRepository.class);
    when(employeeRepository.findAll()).thenReturn(List.of(new Employee(111L, "1", "1")));
  }

  @Test
  void testFindById() {
    String accessToken = getAccessToken(ADMIN_LOGIN, ADMIN_PASSWORD).split(" ")[1];
    JwtAuthFilter authFilter = new JwtAuthFilter(accessToken);
    long id = employeeRepository.findAll().get(0).getId();
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
    long id = employeeRepository.findAll().get(0).getId();
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
        .put(BASE_URL + "/" + id)
        .then()
        .assertThat()
        .statusCode(200);
  }


}
