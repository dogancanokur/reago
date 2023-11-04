package net.okur.reagobs;

import net.okur.reagobs.entity.User;
import net.okur.reagobs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ReagoBsApplication {

  @Autowired PasswordEncoder passwordEncoder;

  public static void main(String[] args) {
    SpringApplication.run(ReagoBsApplication.class, args);
  }

  @Bean
  @Profile("dev")
  CommandLineRunner userCreation(UserRepository userRepository) {
    return args -> {
      for (var i = 1; i <= 60; i++) {
        User user = new User();
        user.setUsername("user_" + i);
        user.setEmail("user%d@reago.com".formatted(i));
        user.setPassword(passwordEncoder.encode("admin"));
        user.setActive(Boolean.TRUE);
        user.setName("%d User Reago".formatted(i));
        userRepository.save(user);
      }
    };
  }
}
