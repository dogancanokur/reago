package net.okur.reagobs.error.exception;

import net.okur.reagobs.service.TranslateService;

public class InvalidTokenException extends RuntimeException {

  public InvalidTokenException() {
    super(TranslateService.getMessage("reago.activate.user-invalid-token"));
  }
}
