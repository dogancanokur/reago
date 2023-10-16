package net.okur.reagobs.service.impl;

import net.okur.reagobs.entity.User;
import net.okur.reagobs.repository.UserRepository;
import net.okur.reagobs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
  public User createUser(User user) {
    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);
    return userRepository.save(user);
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
