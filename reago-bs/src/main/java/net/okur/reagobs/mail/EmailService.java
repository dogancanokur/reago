package net.okur.reagobs.mail;

import net.okur.reagobs.service.TranslateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {
  JavaMailSenderImpl javaMailSender;
  @Autowired
  TranslateService translateService;

  private void initialize() {
    this.javaMailSender = new JavaMailSenderImpl();
    javaMailSender.setHost("smtp.ethereal.email");
    javaMailSender.setPort(587);
    javaMailSender.setUsername("verner84@ethereal.email");
    javaMailSender.setPassword("n2qyMaCGnefr2DgyCf");

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
