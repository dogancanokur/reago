package net.okur.reagobs.service.impl;

import jakarta.transaction.Transactional;
import net.okur.reagobs.dto.input.UserInput;
import net.okur.reagobs.dto.output.UserOutput;
import net.okur.reagobs.entity.User;
import net.okur.reagobs.error.exception.ActivationNotificationException;
import net.okur.reagobs.mail.EmailService;
import net.okur.reagobs.repository.UserRepository;
import net.okur.reagobs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
  public List<User> getAllUser() {
    return userRepository.findAll();
  }

  @Override
  @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = { MailException.class })
  public UserOutput createUser(UserInput userInput) {
    try {

      User user = userInput.toUser();
      String encodedPassword = passwordEncoder.encode(user.getPassword());
      user.setActivationToken(String.valueOf(UUID.randomUUID()));
      user.setPassword(encodedPassword);
      user = userRepository.saveAndFlush(user);
      emailService.sendActivationEmail(user.getEmail(), user.getActivationToken());
      return new UserOutput(user.getId(), userInput.username(), user.getEmail(), user.getActive());

    } catch (MailException mailException) {
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

}
