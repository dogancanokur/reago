package net.okur.reagobs.service;

import net.okur.reagobs.dto.output.UserOutput;
import net.okur.reagobs.entity.User;
import net.okur.reagobs.dto.input.UserInput;

import java.util.List;

public interface UserService {

  List<User> getAllUser();

  UserOutput createUser(UserInput userInput);

  User updateUser(User user);

  void deleteUser(Long id);

  void activateUser(String token);
}
