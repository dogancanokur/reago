package net.okur.reagobs.error.exception;

import net.okur.reagobs.service.TranslateService;

public class ActivationNotificationException extends RuntimeException {
  public ActivationNotificationException() {
    super(TranslateService.getMessage("reago.create-user-mail-failure"));
  }
}
