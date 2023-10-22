package net.okur.reagobs.service.impl;

import net.okur.reagobs.dto.input.UserInput;
import net.okur.reagobs.dto.output.UserOutput;
import net.okur.reagobs.entity.User;
import net.okur.reagobs.repository.UserRepository;
import net.okur.reagobs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = new BCryptPasswordEncoder();
  }

  @Override
  public List<User> getAllUser() {
    return userRepository.findAll();
  }

  @Override
  public UserOutput createUser(UserInput userInput) {
    User user = userInput.toUser();
    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setActivationToken(String.valueOf(UUID.randomUUID()));
    user.setPassword(encodedPassword);
    user = userRepository.save(user);
    return new UserOutput(user.getId(), userInput.username(), user.getEmail(), user.getActive());
  }

  @Override
  public User updateUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }

}
