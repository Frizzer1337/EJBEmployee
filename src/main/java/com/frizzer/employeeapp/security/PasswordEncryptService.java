package com.frizzer.employeeapp.security;

import jakarta.ejb.Stateless;
import org.mindrot.jbcrypt.BCrypt;

@Stateless
public class PasswordEncryptService {
  public String encrypt(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  public boolean checkPassword(String password, String hashedPassword) {
    return BCrypt.checkpw(password, hashedPassword);
  }


}
