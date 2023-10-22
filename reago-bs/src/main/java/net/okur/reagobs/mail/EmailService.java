package net.okur.reagobs.mail;

import jakarta.annotation.PostConstruct;
import net.okur.reagobs.configuration.AppConfig;
import net.okur.reagobs.service.TranslateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {

  private final AppConfig appConfig;
  JavaMailSenderImpl javaMailSender;

  @Autowired
  public EmailService(AppConfig appConfig) {
    this.appConfig = appConfig;
  }

  @PostConstruct
  private void initialize() {
    this.javaMailSender = new JavaMailSenderImpl();

    javaMailSender.setHost(appConfig.getEmail().host());
    javaMailSender.setPort(appConfig.getEmail().port());
    javaMailSender.setUsername(appConfig.getEmail().username());
    javaMailSender.setPassword(appConfig.getEmail().password());

    Properties properties = javaMailSender.getJavaMailProperties();
    properties.put("mail.smtp.starttls.enable", "true");
  }

  public void sendActivationEmail(String email, String activationToken) {
    String activationURL = appConfig.getClient().host() + "/activation/" + activationToken;
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setFrom(appConfig.getEmail().from());
    mailMessage.setTo(email);
    mailMessage.setSubject(TranslateService.getMessageStatic("reago.account-activation"));
    mailMessage.setText(activationURL);

    initialize();
    this.javaMailSender.send(mailMessage);
  }
}
