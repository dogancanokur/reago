package net.okur.reagobs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ReagoBsApplication {

  public static void main(String[] args) {
    SpringApplication.run(ReagoBsApplication.class, args);
  }

}
