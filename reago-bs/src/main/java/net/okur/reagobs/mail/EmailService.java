package net.okur.reagobs.mail;

import jakarta.annotation.PostConstruct;
import net.okur.reagobs.service.TranslateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {
  @Value("${reago.smtp.host}")
  String SMTP_HOST;

  @Value("${reago.smtp.port}")
  int SMTP_PORT;

  @Value("${reago.smtp.username}")
  String SMTP_USERNAME;

  @Value("${reago.smtp.password}")
  String SMTP_PASSWORD;

  JavaMailSenderImpl javaMailSender;
  @Autowired
  TranslateService translateService;

  @PostConstruct
  private void initialize() {
    this.javaMailSender = new JavaMailSenderImpl();
    javaMailSender.setHost(SMTP_HOST);
    javaMailSender.setPort(SMTP_PORT);
    javaMailSender.setUsername(SMTP_USERNAME);
    javaMailSender.setPassword(SMTP_PASSWORD);

    Properties properties = javaMailSender.getJavaMailProperties();
    properties.put("mail.smtp.starttls.enable", "true");
  }

  public void sendActivationEmail(String email, String activationToken) {
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setFrom("noreply@reago.com");
    mailMessage.setTo(email);
    mailMessage.setSubject(TranslateService.getMessageStatic("reago.account-activation"));
    mailMessage.setText("http://localhost:5173/activation/" + activationToken);

    initialize();
    this.javaMailSender.send(mailMessage);
  }
}
