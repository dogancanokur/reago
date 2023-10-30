package net.okur.reagobs.token;

import net.okur.reagobs.dto.request.Credentials;
import net.okur.reagobs.entity.User;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class BasicAuthTokenService implements TokenService {
  @Override
  public Token createToken(User user, Credentials credentials) {
    String base64 = credentials.email() + ":" + credentials.password();
    String token = Base64.getEncoder().encodeToString(base64.getBytes());
    return new Token("Basic", token);
  }

  public User verifyToken(String authorizationToken) {
    return null;
  }
}
