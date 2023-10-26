package net.okur.reagobs.error.exception;

import net.okur.reagobs.service.TranslateService;

public class NotFoundException extends RuntimeException {
  public NotFoundException(long id) {

    super(TranslateService.getMessageFromLocale("reago.not-found-with-args", id));
  }

}
