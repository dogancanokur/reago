package net.okur.reagobs.error.exception;

import net.okur.reagobs.service.TranslateService;

public class AuthenticationException extends RuntimeException {
  public AuthenticationException(String message) {
    super(message);
  }

  public AuthenticationException() {
    super(TranslateService.getMessage("reago.auth.invalid-credentials"));
  }
}
