package dominio.passwords;

import dominio.exceptions.password.PasswordLengthException;

public class PasswordLength extends Validation{

  @Override
  protected boolean condition(String password) {
    return password.length() >= 8;
  }

  @Override
  protected RuntimeException error() {
    return new PasswordLengthException();
  }

 
  
}