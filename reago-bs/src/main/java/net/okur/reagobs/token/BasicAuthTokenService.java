package net.okur.reagobs.token;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import net.okur.reagobs.dto.request.Credentials;
import net.okur.reagobs.entity.User;
import net.okur.reagobs.error.exception.AuthenticationException;
import net.okur.reagobs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BasicAuthTokenService implements TokenService {
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public BasicAuthTokenService(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Token createToken(User user, Credentials credentials) {
    String base64 = credentials.email() + ":" + credentials.password();
    String token = Base64.getEncoder().encodeToString(base64.getBytes());
    return new Token("Basic", token);
  }

  public User verifyToken(String authorizationHeader) {
    if (authorizationHeader == null) {
      throw new AuthenticationException();
    }
    String base64Encoded = authorizationHeader.split("Basic ")[1];
    String decodedValues =
        new String(Base64.getDecoder().decode(base64Encoded), StandardCharsets.UTF_8);
    var credentials = decodedValues.split(":");
    var email = credentials[0];
    var password = credentials[1];
    User byEmail = userService.findByEmail(email);
    if (byEmail == null) {
      throw new AuthenticationException();
    }
    if (passwordEncoder.matches(password, byEmail.getPassword())) {
      return byEmail;
    }
    throw new AuthenticationException();
  }
}
