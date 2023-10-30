package net.okur.reagobs.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import net.okur.reagobs.dto.request.Credentials;
import net.okur.reagobs.dto.response.AuthResponse;
import net.okur.reagobs.error.ApiError;
import net.okur.reagobs.error.exception.AuthenticationException;
import net.okur.reagobs.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
  private final AuthService authService;

  @Autowired
  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/api/v1/auth")
  public ResponseEntity<?> handleAuthentication(@Valid @RequestBody Credentials authenticate) {
    AuthResponse authResponse = authService.authenticate(authenticate);
    return ResponseEntity.ok(authResponse);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException authenticationException,
      HttpServletRequest httpServletRequest) {

    ApiError apiError = new ApiError();
    apiError.setStatus(HttpStatus.UNAUTHORIZED.value());
    apiError.setPath(httpServletRequest.getServletPath());
    apiError.setMessage(authenticationException.getLocalizedMessage());

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(apiError);
  }

}
