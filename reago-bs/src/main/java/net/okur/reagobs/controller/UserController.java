package net.okur.reagobs.controller;

import jakarta.validation.Valid;
import net.okur.reagobs.dto.response.GenericResponse;
import net.okur.reagobs.entity.User;
import net.okur.reagobs.error.ApiError;
import net.okur.reagobs.service.TranslateService;
import net.okur.reagobs.service.UserService;
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
  public GenericResponse createUser(@Valid @RequestBody User user) {

    user = userService.createUser(user);
    return new GenericResponse("User is created. " + user.getUsername());
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
    return new GenericResponse(TranslateService.getMessage("reago.user.delete.success.message"));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  //  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ResponseEntity<ApiError> handleMethodArgNotValidException(MethodArgumentNotValidException exception) {

    ApiError apiError = new ApiError();
    apiError.setPath("/api/v1/users/");
    apiError.setMessage("Validation Error");
    apiError.setStatus(HttpStatus.BAD_REQUEST.value());

    var validationErrors = exception.getBindingResult().getFieldErrors().stream().collect(
        Collectors.toMap(FieldError::getField,
            fieldError -> StringUtils.hasText(fieldError.getDefaultMessage()) ? fieldError.getDefaultMessage() : "",
            (existing, replacing) -> StringUtils.hasText(existing) ? existing + " " + replacing : replacing));

    apiError.setValidationError(validationErrors);

    return ResponseEntity.badRequest().body(apiError);
  }
}
