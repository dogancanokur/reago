package net.okur.reagobs.service;

import net.okur.reagobs.dto.input.UserInput;
import net.okur.reagobs.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

  Page<User> getAllUser(Pageable pageable);

  void createUser(UserInput userInput);

  User updateUser(User user);

  void deleteUser(Long id);

  void activateUser(String token);
}
