package com.belstu.thesisproject.exception;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(final String id) {
    super(String.format("User with %s id not found", id));
  }
}
