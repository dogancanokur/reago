package net.okur.reagobs.error.exception;

public class AuthorizationException extends RuntimeException {
  public AuthorizationException() {
    super("Forbidden");
  }
}
