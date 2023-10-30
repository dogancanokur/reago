package net.okur.reagobs.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import net.okur.reagobs.dto.input.UserInput;
import net.okur.reagobs.dto.output.UserOutput;
import net.okur.reagobs.dto.response.GenericResponse;
import net.okur.reagobs.entity.User;
import net.okur.reagobs.error.ApiError;
import net.okur.reagobs.error.exception.ActivationNotificationException;
import net.okur.reagobs.error.exception.InvalidTokenException;
import net.okur.reagobs.error.exception.NotFoundException;
import net.okur.reagobs.service.TranslateService;
import net.okur.reagobs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
