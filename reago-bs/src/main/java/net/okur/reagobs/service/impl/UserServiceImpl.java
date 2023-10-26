package net.okur.reagobs.service.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import net.okur.reagobs.dto.input.UserInput;
import net.okur.reagobs.dto.output.UserOutput;
import net.okur.reagobs.entity.User;
import net.okur.reagobs.error.exception.ActivationNotificationException;
import net.okur.reagobs.error.exception.InvalidTokenException;
import net.okur.reagobs.mail.EmailService;
import net.okur.reagobs.repository.UserRepository;
import net.okur.reagobs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final EmailService emailService;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, EmailService emailService) {
    this.userRepository = userRepository;
    this.emailService = emailService;
    this.passwordEncoder = new BCryptPasswordEncoder();
  }

  @Override
  public Page<UserOutput> getAllUser(Pageable pageable) {
    return userRepository.findAll(pageable).map(UserOutput::new);
  }

  @Override
  @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = { Exception.class })
  public UserOutput createUser(UserInput userInput) {
    try {

      User user = userInput.toUser();
      String encodedPassword = passwordEncoder.encode(user.getPassword());
      user.setActivationToken(String.valueOf(UUID.randomUUID()));
      user.setPassword(encodedPassword);
      user = userRepository.saveAndFlush(user);
      emailService.sendActivationEmail(user.getEmail(), user.getUsername(), user.getActivationToken());

      return new UserOutput(user);

    } catch (Exception e) {
      throw new ActivationNotificationException();
    }
  }

  @Override
  public User updateUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }

  @Override
  public void activateUser(@Valid @NotBlank String token) throws InvalidTokenException {
    User byActivationToken = userRepository.findByActivationToken(token);
    if (byActivationToken == null) {
      throw new InvalidTokenException();
    }
    byActivationToken.setActive(Boolean.TRUE);
    byActivationToken.setActivationToken(null);
    userRepository.save(byActivationToken);
  }

}
