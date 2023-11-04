package net.okur.reagobs.controller;

import jakarta.validation.Valid;
import net.okur.reagobs.dto.input.UserInput;
import net.okur.reagobs.dto.input.UserSaveInput;
import net.okur.reagobs.dto.output.UserOutput;
import net.okur.reagobs.dto.response.GenericResponse;
import net.okur.reagobs.service.TranslateService;
import net.okur.reagobs.service.UserService;
import net.okur.reagobs.token.BasicAuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

  private final UserService userService;
  private final TranslateService translateService;
  private final BasicAuthTokenService tokenService;

  @Autowired
  public UserController(
      UserService userService,
      TranslateService translateService,
      @Qualifier("basicAuthTokenService") BasicAuthTokenService tokenService) {
    this.userService = userService;
    this.translateService = translateService;
    this.tokenService = tokenService;
  }

  @PostMapping("/api/v1/users/")
  public GenericResponse createUser(@Valid @RequestBody UserSaveInput userInput) {

    userService.createUser(userInput);
    String translateMessage = TranslateService.getMessage("reago.user.create.success.message");
    return new GenericResponse(translateMessage);
  }

  @GetMapping("/api/v1/users/")
  public ResponseEntity<Page<UserOutput>> getAllUsers(
      Pageable pageable,
      // (@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10", name =
      // "pageSize") int size)
      @RequestHeader(name = "Authorization", required = false) String authorizationHeader) {

    var loggedInUser = tokenService.verifyToken(authorizationHeader);

    return ResponseEntity.ok(userService.getAllUser(pageable, loggedInUser));
  }

  @GetMapping("/api/v1/users/{userId}")
  public ResponseEntity<UserOutput> getByUserId(@PathVariable Long userId) {
    return ResponseEntity.ok(userService.getByUserId(userId));
  }

  @PutMapping("/api/v1/users/{userId}")
  public ResponseEntity<UserOutput> updateUser(
      @PathVariable Long userId,
      @Valid @RequestBody UserInput userInput,
      @RequestHeader(name = "Authorization") String authHeader) {

    UserOutput user = userService.updateUser(userId, userInput, authHeader);
    return ResponseEntity.ok(user);
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
