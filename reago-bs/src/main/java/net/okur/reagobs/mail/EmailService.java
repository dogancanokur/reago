package net.okur.reagobs.mail;

import jakarta.annotation.PostConstruct;
import jakarta.mail.internet.MimeMessage;
import net.okur.reagobs.configuration.AppConfig;
import net.okur.reagobs.service.TranslateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Service
public class EmailService {

  private final AppConfig appConfig;
  JavaMailSenderImpl javaMailSender;
  private String activationEmail = """
      <html>
        <h1>Active Account</h1>
        <body>
          <a href=${url}">Click Here</a>
        </body>
      </html>""";

  private String mailTest = "activationMail.html";

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

  public void sendActivationEmail(String email, String username, String activationToken) throws Exception {

    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");

    message.setFrom(appConfig.getEmail().from());
    message.setTo(email);
    message.setSubject(TranslateService.getMessage("reago.account-activation"));

    String mailBody = createActivationMail(username, activationToken);
    message.setText(mailBody, true);

    this.javaMailSender.send(mimeMessage);
  }

  private String createActivationMail(String username, String activationToken) throws IOException {
    String htmlFilePath = appConfig.getActivationMailHtml() + "_" + LocaleContextHolder.getLocale() + ".html";
    Resource resource = new ClassPathResource(htmlFilePath);

    InputStream inputStream = resource.getInputStream();
    byte[] byteArray = FileCopyUtils.copyToByteArray(inputStream);
    String mailBody = new String(byteArray, StandardCharsets.UTF_8);

    String activationURL = appConfig.getClient().host() + "/activation/" + activationToken;
    mailBody = mailBody.replace("${url}", activationURL).replace("${username}", username);
    return mailBody;
  }
}
