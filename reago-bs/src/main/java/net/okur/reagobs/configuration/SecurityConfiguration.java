package net.okur.reagobs.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * This class is used to configure the security settings for the application. It is annotated
 * with @Configuration to indicate that it is a configuration class. It is also annotated
 * with @EnableWebSecurity and @EnableMethodSecurity(prePostEnabled = true) to enable Spring
 * Security's web security support and method-level security respectively.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

  /**
   * This method is used to configure the security filter chain. It is annotated with @Bean to
   * indicate that it is a bean to be managed by the Spring container. It takes an HttpSecurity
   * object as a parameter and configures it to authorize certain HTTP requests, disable headers and
   * CSRF, and set the authentication entry point. It then builds the HttpSecurity object and
   * returns it as a SecurityFilterChain.
   *
   * @param httpSecurity the HttpSecurity object to be configured
   * @return the configured SecurityFilterChain
   * @throws Exception if an error occurs during the configuration
   */
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

    httpSecurity.authorizeHttpRequests(
        authorizationManagerRequestMatcherRegistry ->
            authorizationManagerRequestMatcherRegistry
                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT, "/api/v1/users/"))
                .authenticated()
                .anyRequest()
                .permitAll());

    httpSecurity.headers(AbstractHttpConfigurer::disable);
    httpSecurity.csrf(AbstractHttpConfigurer::disable);
    httpSecurity.httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(new AuthEntryPoint()));
    return httpSecurity.build();
  }

  /**
   * This method is used to configure the password encoder. It is annotated with @Bean to indicate
   * that it is a bean to be managed by the Spring container. It returns a new BCryptPasswordEncoder
   * object.
   *
   * @return the configured PasswordEncoder
   */
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
