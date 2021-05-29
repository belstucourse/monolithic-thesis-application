package com.belstu.thesisproject.exception;

public class NotFoundException extends RuntimeException {
  public NotFoundException(final String id) {
    super(String.format("User with %s id not found", id));
  }
}
