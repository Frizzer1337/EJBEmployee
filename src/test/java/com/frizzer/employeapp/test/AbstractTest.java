package com.frizzer.employeapp.test;

import com.google.gson.JsonObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import lombok.SneakyThrows;

public abstract class AbstractTest {

  protected final String BASE_URL = "http://localhost:6005/EmployeeApp-1.0-SNAPSHOT/api/employees";
  protected final String ADMIN_LOGIN = "paсka111232d3";
  protected final String ADMIN_PASSWORD = "232323233";
  protected final String JSON = "application/json";
  protected final String AUTHORIZATION = "authorization";
  protected final String BAD_AUTHORIZATION = "bad_auth";

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

}
