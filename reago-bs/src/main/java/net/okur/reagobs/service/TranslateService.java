package net.okur.reagobs.service;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

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

  public static String getMessageFromLocale(String messageKey, Object... args) {
    String message = getMessage(messageKey);
    return MessageFormat.format(message, args);
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
