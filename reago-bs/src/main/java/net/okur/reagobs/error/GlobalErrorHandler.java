package net.okur.reagobs.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler({DisabledException.class, AccessDeniedException.class})
  ResponseEntity<ApiError> handleException(Exception exception, HttpServletRequest request) {

    ApiError apiError = new ApiError();
    apiError.setMessage(exception.getLocalizedMessage());
    if (exception instanceof AccessDeniedException) {
      apiError.setStatus(403);

    } else if (exception instanceof DisabledException) {
      apiError.setStatus(401);

    } else {
      apiError.setStatus(500);
    }
    apiError.setPath(request.getRequestURI());
    return ResponseEntity.status(401).body(apiError);
  }
}
