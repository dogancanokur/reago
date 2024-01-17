package net.okur.reagobs.mail;

import jakarta.annotation.PostConstruct;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
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

/**
 * This class is used to send emails.
 * It is annotated with @Service to indicate that it is a service class.
 * It uses the JavaMailSenderImpl class to send emails.
 */
@Service
public class EmailService {

  private final AppConfig appConfig;
  JavaMailSenderImpl javaMailSender;

  /**
   * The HTML content of the activation email.
   */
  private final String activationEmail = """
      <html>
        <h1>Active Account</h1>
        <body>
          <a href=${url}">Click Here</a>
        </body>
      </html>""";

  private String mailTest = "activationMail.html";

  /**
   * Constructs a new EmailService with the specified AppConfig.
   *
   * @param appConfig the AppConfig to be used for email configuration
   */
  @Autowired
  public EmailService(AppConfig appConfig) {
    this.appConfig = appConfig;
  }

  /**
   * Initializes the JavaMailSenderImpl with the email configuration from the AppConfig.
   */
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

  /**
   * Sends an activation email to the specified email address.
   *
   * @param email the email address to which the activation email is sent
   * @param username the username of the user
   * @param activationToken the activation token for the user
   * @throws Exception if an error occurs while sending the email
   */
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

  /**
   * Creates the body of the activation email.
   *
   * @param username the username of the user
   * @param activationToken the activation token for the user
   * @return the body of the activation email
   * @throws IOException if an error occurs while reading the HTML file
   */
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