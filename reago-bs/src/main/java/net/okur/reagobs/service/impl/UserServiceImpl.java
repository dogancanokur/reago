package net.okur.reagobs.service.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.Optional;
import java.util.UUID;
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
import net.okur.reagobs.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final EmailService emailService;
  private final PasswordEncoder passwordEncoder;
  private final TokenService tokenService;

  @Autowired
  public UserServiceImpl(
      UserRepository userRepository,
      EmailService emailService,
      PasswordEncoder passwordEncoder,
      @Qualifier("basicAuthTokenService") @Lazy TokenService tokenService) {
    this.userRepository = userRepository;
    this.emailService = emailService;
    this.passwordEncoder = passwordEncoder;
    this.tokenService = tokenService;
  }

  @Override
  public Page<UserOutput> getAllUser(Pageable pageable, User loggedInUser) {
    if (loggedInUser == null) {
      return userRepository.findAll(pageable).map(UserOutput::new);
    }
    return userRepository.findByIdNot(loggedInUser.getId(), pageable).map(UserOutput::new);
  }

  @Override
  public UserOutput getByUserId(Long id) {
    return new UserOutput(userRepository.findById(id).orElseThrow(() -> new NotFoundException(id)));
  }

  @Override
  @Transactional(
      value = Transactional.TxType.REQUIRED,
      rollbackOn = {Exception.class})
  public UserOutput createUser(UserSaveInput userSaveInput) {
    try {

      User user = userSaveInput.toUser();
      String encodedPassword = passwordEncoder.encode(user.getPassword());
      user.setActivationToken(String.valueOf(UUID.randomUUID()));
      user.setPassword(encodedPassword);
      user = userRepository.saveAndFlush(user);
      emailService.sendActivationEmail(
          user.getEmail(), user.getUsername(), user.getActivationToken());

      return new UserOutput(user);

    } catch (Exception e) {
      throw new ActivationNotificationException();
    }
  }

  @Override
  public UserOutput updateUser(Long userId, UserInput userInput, String authHeader) {

    User authorizedUser = tokenService.verifyToken(authHeader);
    if (!authorizedUser.getId().equals(userId)) {
      throw new AuthorizationException();
    }

    userRepository
        .findByUsername(userInput.getUsername())
        .ifPresent(
            user -> {
              if (!user.getId().equals(userId)) {
                throw new NotFoundException("Username already exists");
              }
            });

    User user = findByUserId(userId).orElseThrow(() -> new NotFoundException(userId));
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
  public User findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public Optional<User> findByUserId(Long id) {
    return userRepository.findById(id);
  }
}
