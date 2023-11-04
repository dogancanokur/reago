package net.okur.reagobs.service.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.Optional;
import java.util.UUID;
import net.okur.reagobs.configuration.CurrentUser;
import net.okur.reagobs.dto.input.UserInput;
import net.okur.reagobs.dto.input.UserSaveInput;
import net.okur.reagobs.dto.output.UserOutput;
import net.okur.reagobs.entity.User;
import net.okur.reagobs.error.exception.ActivationNotificationException;
import net.okur.reagobs.error.exception.AuthorizationException;
import net.okur.reagobs.error.exception.InvalidTokenException;
import net.okur.reagobs.error.exception.NotFoundException;
import net.okur.reagobs.mail.EmailService;
import net.okur.reagobs.repository.UserRepository;
import net.okur.reagobs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final EmailService emailService;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserServiceImpl(
      UserRepository userRepository, EmailService emailService, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.emailService = emailService;
    this.passwordEncoder = passwordEncoder;
  }

  public static CurrentUser getUserPrincipal() {
    return (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  @Override
  public Page<UserOutput> getAllUser(Pageable pageable, Long userId) {
    if (userId == null) {
      return userRepository.findAll(pageable).map(UserOutput::new);
    }
    return userRepository.findByIdNot(userId, pageable).map(UserOutput::new);
  }

  @Override
  public UserOutput getByUserId(Long id) {
    return new UserOutput(userRepository.findById(id).orElseThrow(() -> new NotFoundException(id)));
  }

  @Override
  @Transactional(
      value = Transactional.TxType.REQUIRED,
      rollbackOn = {Exception.class})
  public void createUser(UserSaveInput userSaveInput) {
    try {

      User user = userSaveInput.toUser();
      String encodedPassword = passwordEncoder.encode(user.getPassword());
      user.setActivationToken(String.valueOf(UUID.randomUUID()));
      user.setPassword(encodedPassword);
      user = userRepository.saveAndFlush(user);
      emailService.sendActivationEmail(
          user.getEmail(), user.getUsername(), user.getActivationToken());

      new UserOutput(user);

    } catch (Exception e) {
      throw new ActivationNotificationException();
    }
  }

  @Override
  public UserOutput updateUser(UserInput userInput) {
    Long userId = userInput.getId();
    userRepository
        .findByUsername(userInput.getUsername())
        .ifPresent(
            user -> {
              if (!user.getId().equals(userId)) {
                throw new NotFoundException("Username already exists");
              }
            });

    User user = findByUserId(userId).orElseThrow(() -> new NotFoundException(userId));
    if (!user.getId().equals(UserServiceImpl.getUserPrincipal().getId())) {
      throw new AuthorizationException();
    }
    user.setUsername(userInput.getUsername());
    return new UserOutput(userRepository.save(user));
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

  @Override
  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public Optional<User> findByUserId(Long id) {
    return userRepository.findById(id);
  }
}
