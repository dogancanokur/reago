package net.okur.reagobs.service;

import java.util.Optional;
import net.okur.reagobs.dto.input.UserInput;
import net.okur.reagobs.dto.input.UserSaveInput;
import net.okur.reagobs.dto.output.UserOutput;
import net.okur.reagobs.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

  Page<UserOutput> getAllUser(Pageable pageable, Long userId);

  UserOutput getByUserId(Long id);

  void createUser(UserSaveInput userSaveInput);

  UserOutput updateUser(UserInput userInput);

  void deleteUser(Long id);

  void activateUser(String token);

  Optional<User> findByEmail(String email);

  Optional<User> findByUserId(Long id);
}
