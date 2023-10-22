package net.okur.reagobs.error.exception;

import net.okur.reagobs.service.TranslateService;

public class ActivationNotificationException extends RuntimeException {
  public ActivationNotificationException() {
    super(TranslateService.getMessageStatic("reago.create-user-mail-failure"));
  }
}
