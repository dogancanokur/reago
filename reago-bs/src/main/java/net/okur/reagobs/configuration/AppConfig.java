package net.okur.reagobs.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@ConfigurationProperties(prefix = "reago")
@Configuration
public class AppConfig {

  @Setter
  private Client client;
  @Setter
  private Email email;
  @Setter
  private String activationMailHtml;

  public record Email(String host, int port, String username, String password, String from) {

  }

  public record Client(String host) {

  }
}
