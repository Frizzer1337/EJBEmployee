package com.frizzer.employeeapp.security;

import com.frizzer.employeeapp.entity.EmployeeRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import java.security.Key;
import java.time.Duration;
import java.util.Date;

@Startup
@Singleton
public class JwtTokenService {

  private Key key;

  @PostConstruct
  public void init() {
    key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
  }

  public String generateToken(String username, EmployeeRole role) {
    Date expiration = new Date(System.currentTimeMillis() + Duration.ofHours(1).toMillis());
    return Jwts.builder()
        .setSubject(username)
        .claim("role", role.name())
        .setExpiration(expiration)
        .signWith(key)
        .compact();
  }

  public Jws<Claims> parseToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token);
  }
}