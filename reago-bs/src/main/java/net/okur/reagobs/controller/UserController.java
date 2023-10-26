package net.okur.reagobs.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import net.okur.reagobs.dto.input.UserInput;
import net.okur.reagobs.dto.output.UserOutput;
import net.okur.reagobs.dto.response.GenericResponse;
import net.okur.reagobs.entity.User;
import net.okur.reagobs.error.ApiError;
import net.okur.reagobs.error.exception.ActivationNotificationException;
import net.okur.reagobs.error.exception.NotFoundException;
import net.okur.reagobs.error.exception.InvalidTokenException;
import net.okur.reagobs.service.TranslateService;
import net.okur.reagobs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
public class UserController {

  private final UserService userService;
  private final TranslateService translateService;

  @Autowired
  public UserController(UserService userService, TranslateService translateService) {
    this.userService = userService;
    this.translateService = translateService;
  }

  @PostMapping("/api/v1/users/")
  public GenericResponse createUser(@Valid @RequestBody UserInput userInput) {

    userService.createUser(userInput);
    String translateMessage = TranslateService.getMessage("reago.user.create.success.message");
    return new GenericResponse(translateMessage);
  }

  @GetMapping("/api/v1/users/")
  public ResponseEntity<Page<UserOutput>> getAllUsers(Pageable pageable) {
    //(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10", name = "pageSize") int size)

    return ResponseEntity.ok(userService.getAllUser(pageable));
  }

  @GetMapping("/api/v1/users/{userId}")
  public ResponseEntity<UserOutput> getByUserUd(@PathVariable Long userId) {
    return ResponseEntity.ok(userService.getByUserId(userId));
  }

  @PutMapping("/api/v1/users/")
  public GenericResponse updateUser(@RequestBody User user) {
    userService.updateUser(user);
    return new GenericResponse(
        translateService.getMessageWithArgs("reago.user.update.success.message", user.getUsername()));
  }

  @DeleteMapping("/api/v1/users/{id}")
  public GenericResponse deleteUser(@PathVariable("id") Long id) {
    userService.deleteUser(id);
    return new GenericResponse(TranslateService.getMessage("reago.user.delete.success.message"));
  }

  @PatchMapping("/api/v1/users/{token}/active")
  public GenericResponse activateUser(@PathVariable String token) {
    userService.activateUser(token);
    String message = TranslateService.getMessage("reago.user-activated-successfully");
    return new GenericResponse(message);

  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  //  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ResponseEntity<ApiError> handleMethodArgNotValidException(MethodArgumentNotValidException exception) {

    ApiError apiError = new ApiError();
    apiError.setPath("/api/v1/users/");
    apiError.setMessage(TranslateService.getMessage("reago.validation-error"));
    apiError.setStatus(HttpStatus.BAD_REQUEST.value());

    var validationErrors = exception.getBindingResult().getFieldErrors().stream().collect(
        Collectors.toMap(FieldError::getField,
            fieldError -> StringUtils.hasText(fieldError.getDefaultMessage()) ? fieldError.getDefaultMessage() : "",
            (existing, replacing) -> StringUtils.hasText(existing) ? existing + " " + replacing : replacing));

    apiError.setValidationError(validationErrors);

    return ResponseEntity.badRequest().body(apiError);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  private ResponseEntity<ApiError> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {

    ApiError apiError = new ApiError();
    apiError.setPath("/api/v1/users/");
    apiError.setMessage(TranslateService.getMessage("reago.something-went-wrong"));
    apiError.setStatus(HttpStatus.BAD_REQUEST.value());

    return ResponseEntity.badRequest().body(apiError);
  }

  @ExceptionHandler(ActivationNotificationException.class)
  private ResponseEntity<ApiError> handleActivationNotificationException(ActivationNotificationException exception,
      HttpServletRequest httpServletRequest) {

    ApiError apiError = new ApiError();
    apiError.setPath(httpServletRequest.getRequestURI());
    apiError.setMessage(exception.getMessage());
    int status = HttpStatus.BAD_GATEWAY.value();
    apiError.setStatus(status);

    return ResponseEntity.status(status).body(apiError);
  }

  @ExceptionHandler(InvalidTokenException.class)
  private ResponseEntity<ApiError> handleInvalidTokenException(InvalidTokenException exception,
      HttpServletRequest httpServletRequest) {

    ApiError apiError = new ApiError();
    apiError.setPath(httpServletRequest.getRequestURI());
    apiError.setMessage(exception.getMessage());
    int status = HttpStatus.BAD_REQUEST.value();
    apiError.setStatus(status);

    return ResponseEntity.status(status).body(apiError);
  }

  @ExceptionHandler(NotFoundException.class)
  private ResponseEntity<ApiError> handleEntityNotFoundException(NotFoundException exception,
      HttpServletRequest httpServletRequest) {

    ApiError apiError = new ApiError();
    apiError.setPath(httpServletRequest.getRequestURI());
    apiError.setMessage(exception.getMessage());
    int status = HttpStatus.BAD_REQUEST.value();
    apiError.setStatus(status);

    return ResponseEntity.status(status).body(apiError);

  }

}
