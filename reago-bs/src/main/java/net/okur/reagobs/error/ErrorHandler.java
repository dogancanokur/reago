package net.okur.reagobs.error;

import jakarta.servlet.http.HttpServletRequest;
import net.okur.reagobs.error.exception.ActivationNotificationException;
import net.okur.reagobs.error.exception.InvalidTokenException;
import net.okur.reagobs.error.exception.NotFoundException;
import net.okur.reagobs.service.TranslateService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler {

  @ExceptionHandler({ //
      NotFoundException.class, //
      InvalidTokenException.class, //
      MethodArgumentNotValidException.class, //
      DataIntegrityViolationException.class, //
      ActivationNotificationException.class //
  })
  private ResponseEntity<ApiError> handleException(Exception exception, HttpServletRequest httpServletRequest) {

    ApiError apiError = new ApiError();
    apiError.setPath(httpServletRequest.getRequestURI());
    int status = HttpStatus.BAD_REQUEST.value();
    apiError.setMessage(exception.getMessage());

    if (exception instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
      apiError.setMessage(TranslateService.getMessage("reago.validation-error"));
      Map<String, String> validationErrors =
          methodArgumentNotValidException.getBindingResult().getFieldErrors().stream().collect(
              Collectors.toMap(FieldError::getField, fieldError -> StringUtils.hasText(fieldError.getDefaultMessage())
                      ? fieldError.getDefaultMessage()
                      : "",
                  (existing, replacing) -> StringUtils.hasText(existing) ? existing + " " + replacing : replacing));
      apiError.setValidationError(validationErrors);
      status = HttpStatus.BAD_REQUEST.value();

    } else if (exception instanceof DataIntegrityViolationException) {
      apiError.setMessage(TranslateService.getMessage("reago.something-went-wrong"));
      status = HttpStatus.BAD_REQUEST.value();

    } else if (exception instanceof ActivationNotificationException) {
      status = HttpStatus.BAD_GATEWAY.value();

    }

    apiError.setStatus(status);
    return ResponseEntity.status(status).body(apiError);
  }

}
