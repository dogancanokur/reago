package net.okur.reagobs.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import net.okur.reagobs.dto.input.UserInput;
import net.okur.reagobs.dto.input.UserSaveInput;
import net.okur.reagobs.dto.output.UserOutput;
import net.okur.reagobs.dto.response.GenericResponse;
import net.okur.reagobs.service.TranslateService;
import net.okur.reagobs.service.UserService;
import net.okur.reagobs.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/api/v1/users/")
  public GenericResponse createUser(@Valid @RequestBody UserSaveInput userInput) {

    userService.createUser(userInput);
    String translateMessage = TranslateService.getMessage("reago.user.create.success.message");
    return new GenericResponse(translateMessage);
  }

  @GetMapping("/api/v1/users/")
  public ResponseEntity<Page<UserOutput>> getAllUsers(Pageable pageable) {

    try {
      var userId = UserServiceImpl.getUserPrincipal().getId();
      return ResponseEntity.ok(userService.getAllUser(pageable, userId));

    } catch (Exception e) {
      return ResponseEntity.ok(userService.getAllUser(pageable, null));
    }
  }

  @GetMapping("/api/v1/users/{userId}")
  public ResponseEntity<UserOutput> getByUserId(@PathVariable Long userId) {
    return ResponseEntity.ok(userService.getByUserId(userId));
  }

  @PutMapping("/api/v1/users/")
  public ResponseEntity<UserOutput> updateUser(@Valid @RequestBody UserInput userInput) {

    UserOutput user = userService.updateUser(userInput);
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
