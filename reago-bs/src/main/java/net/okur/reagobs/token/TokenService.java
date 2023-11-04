package net.okur.reagobs.token;

import net.okur.reagobs.dto.request.Credentials;
import net.okur.reagobs.entity.User;

public interface TokenService {

  Token createToken(User user, Credentials credentials);
}
