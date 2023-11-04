package net.okur.reagobs.token;

import java.util.Base64;
import net.okur.reagobs.dto.request.Credentials;
import net.okur.reagobs.entity.User;
import org.springframework.stereotype.Service;

@Service
public class BasicAuthTokenService implements TokenService {

  @Override
  public Token createToken(User user, Credentials credentials) {
    String base64 = credentials.email() + ":" + credentials.password();
    String token = Base64.getEncoder().encodeToString(base64.getBytes());
    return new Token("Basic", token);
  }
}
