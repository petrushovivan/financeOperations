package com.example.financeOperations.exeption;

public class UserAlreadyExistException extends RuntimeException {
  public UserAlreadyExistException(String message) {super(message);}
}
