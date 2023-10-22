package net.okur.reagobs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.ResourceBundle;

@Service
public class TranslateService {

  private final MessageSource messageSource;

  @Autowired
  public TranslateService(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public static String getMessage(String standardMessage) {
    return ResourceBundle.getBundle("messages", LocaleContextHolder.getLocale()).getString(standardMessage);
  }

  public String getMessageWithArgs(String standardMessage, String... args) {

    return messageSource.getMessage(standardMessage, args, LocaleContextHolder.getLocale());
  }
}
