package com.belstu.thesisproject.exception;

public class UserNotFoundException extends Exception {
  public UserNotFoundException(final String id) {
    super(String.format("User with %s id not found", id));
  }
}
