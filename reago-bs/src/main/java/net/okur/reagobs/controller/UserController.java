package net.okur.reagobs.controller;

import net.okur.reagobs.dto.response.GenericResponse;
import net.okur.reagobs.entity.User;
import net.okur.reagobs.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/api/v1/users/")
  public GenericResponse createUser(@RequestBody User user) {
    user = userService.createUser(user);
    return new GenericResponse("User is created. " + user.getUsername());
  }

  @PutMapping("/api/v1/users/")
  public GenericResponse updateUser(@RequestBody User user) {
    userService.updateUser(user);
    return new GenericResponse("User is updated. " + user.getUsername());
  }

  @DeleteMapping("/api/v1/users/{id}")
  public GenericResponse deleteUser(@PathVariable("id") Long id) {
    userService.deleteUser(id);
    return new GenericResponse("User is deleted.");
  }
}
