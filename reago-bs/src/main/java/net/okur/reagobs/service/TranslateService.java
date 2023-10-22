package net.okur.reagobs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Service
public class TranslateService {

  private final MessageSource messageSource;

  @Autowired
  public TranslateService(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public static String getMessage(String standardMessage) {
    Locale locale = LocaleContextHolder.getLocale();
    try {
      return ResourceBundle.getBundle("messages", locale).getString(standardMessage);

    } catch (MissingResourceException exception) {
      return "??_%s_??_%s".formatted(standardMessage, locale);
    }
  }

  public String getMessageWithArgs(String standardMessage, String... args) {
    Locale locale = LocaleContextHolder.getLocale();
    try {
      return messageSource.getMessage(standardMessage, args, locale);

    } catch (NoSuchMessageException exception) {
      return "??_%s_??_%s".formatted(standardMessage, locale);
    }
  }

}
