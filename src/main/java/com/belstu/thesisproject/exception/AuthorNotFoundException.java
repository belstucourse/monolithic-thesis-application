package com.belstu.thesisproject.exception;

public class AuthorNotFoundException extends Exception {
  public AuthorNotFoundException(final String id) {
    super(String.format("Author with %s id not found", id));
  }
}
