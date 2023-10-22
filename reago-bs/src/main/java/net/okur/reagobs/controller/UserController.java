package net.okur.reagobs.controller;

import jakarta.validation.Valid;
import net.okur.reagobs.dto.output.UserOutput;
import net.okur.reagobs.dto.response.GenericResponse;
import net.okur.reagobs.entity.User;
import net.okur.reagobs.error.ApiError;
import net.okur.reagobs.error.exception.ActivationNotificationException;
import net.okur.reagobs.service.TranslateService;
import net.okur.reagobs.service.UserService;
import net.okur.reagobs.dto.input.UserInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    UserOutput userOutput = userService.createUser(userInput);
    String translateMessage =
        translateService.getMessageWithArgs("reago.user.create.success.message", userOutput.username());
    return new GenericResponse(translateMessage);
  }

  @GetMapping("/api/v1/users/")
  public ResponseEntity<?> getAllUsers() {

    return ResponseEntity.ok(userService.getAllUser());
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
    return new GenericResponse(TranslateService.getMessageStatic("reago.user.delete.success.message"));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  //  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ResponseEntity<ApiError> handleMethodArgNotValidException(MethodArgumentNotValidException exception) {

    ApiError apiError = new ApiError();
    apiError.setPath("/api/v1/users/");
    apiError.setMessage(TranslateService.getMessageStatic("reago.validation-error"));
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
    apiError.setMessage(TranslateService.getMessageStatic("reago.something-went-wrong"));
    apiError.setStatus(HttpStatus.BAD_REQUEST.value());

    return ResponseEntity.badRequest().body(apiError);
  }

  @ExceptionHandler(ActivationNotificationException.class)
  private ResponseEntity<ApiError> handleActivationNotificationException(ActivationNotificationException exception) {

    ApiError apiError = new ApiError();
    apiError.setPath("/api/v1/users/");
    apiError.setMessage(exception.getMessage());
    int status = HttpStatus.BAD_GATEWAY.value();
    apiError.setStatus(status);

    return ResponseEntity.status(status).body(apiError);
  }
}
