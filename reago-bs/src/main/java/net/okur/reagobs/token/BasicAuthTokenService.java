package net.okur.reagobs.token;

import net.okur.reagobs.dto.request.Credentials;
import net.okur.reagobs.entity.User;
import net.okur.reagobs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

@Service
public class BasicAuthTokenService implements TokenService {
  private final UserService userService;
  PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Autowired
  public BasicAuthTokenService(UserService userService) {
    this.userService = userService;
  }

  @Override
  public Token createToken(User user, Credentials credentials) {
    String base64 = credentials.email() + ":" + credentials.password();
    String token = Base64.getEncoder().encodeToString(base64.getBytes());
    return new Token("Basic", token);
  }

  public User verifyToken(String authorizationHeader) {
    if (authorizationHeader == null) {
      return null;
    }
    String base64Encoded = authorizationHeader.split("Basic ")[1];
    String decodedValues = new String(Base64.getDecoder().decode(base64Encoded), StandardCharsets.UTF_8);
    var credentials = decodedValues.split(":");
    var email = credentials[0];
    var password = credentials[1];
    User byEmail = userService.findByEmail(email);
    if (byEmail == null) {
      return null;
    }
    if (passwordEncoder.matches(password, byEmail.getPassword())) {
      return byEmail;
    }
    return null;
  }
}
