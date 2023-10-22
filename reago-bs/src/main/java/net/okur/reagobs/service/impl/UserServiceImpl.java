package net.okur.reagobs.service.impl;

import jakarta.transaction.Transactional;
import net.okur.reagobs.dto.input.UserInput;
import net.okur.reagobs.dto.output.UserOutput;
import net.okur.reagobs.entity.User;
import net.okur.reagobs.repository.UserRepository;
import net.okur.reagobs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;
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
  @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = { MailException.class })
  public UserOutput createUser(UserInput userInput) {
    try {

      User user = userInput.toUser();
      String encodedPassword = passwordEncoder.encode(user.getPassword());
      user.setActivationToken(String.valueOf(UUID.randomUUID()));
      user.setPassword(encodedPassword);
      user = userRepository.saveAndFlush(user);
      sendActivationEmail(user);
      return new UserOutput(user.getId(), userInput.username(), user.getEmail(), user.getActive());
    } catch (DataIntegrityViolationException exception) {
      //todo : exception
      throw exception;
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

  private void sendActivationEmail(User user) {
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setFrom("noreply@reago.com");
    mailMessage.setTo(user.getEmail());
    mailMessage.setSubject("Account Activation");
    mailMessage.setText("http://localhost:5173/activation/" + user.getActivationToken());

    getJavaMailSender().send(mailMessage);
  }

  public JavaMailSender getJavaMailSender() {
    JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
    javaMailSender.setHost("smtp.ethereal.email");
    javaMailSender.setPort(587);
    javaMailSender.setUsername("verner84@ethereal.email");
    javaMailSender.setPassword("n2qyMaCGnefr2DgyCf");

    Properties properties = javaMailSender.getJavaMailProperties();
    properties.put("mail.smtp.starttls.enable", "true");

    return javaMailSender;
  }
}
