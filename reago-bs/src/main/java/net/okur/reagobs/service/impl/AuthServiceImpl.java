package net.okur.reagobs.service.impl;

import net.okur.reagobs.dto.output.UserOutput;
import net.okur.reagobs.dto.request.Credentials;
import net.okur.reagobs.dto.response.AuthResponse;
import net.okur.reagobs.entity.User;
import net.okur.reagobs.error.exception.AuthenticationException;
import net.okur.reagobs.service.AuthService;
import net.okur.reagobs.service.UserService;
import net.okur.reagobs.token.Token;
import net.okur.reagobs.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

  private final UserService userService;
  private final TokenService tokenService;
  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Autowired
  public AuthServiceImpl(
      UserService userService, @Qualifier("basicAuthTokenService") TokenService tokenService) {
    this.userService = userService;
    this.tokenService = tokenService;
  }

  @Override
  public AuthResponse authenticate(Credentials credentials) {
    User user = userService.findByEmail(credentials.email());
    if (user == null || !passwordEncoder.matches(credentials.password(), user.getPassword())) {
      throw new AuthenticationException();
    }

    Token token = tokenService.createToken(user, credentials);

    AuthResponse authResponse = new AuthResponse();
    authResponse.setToken(token);
    authResponse.setUserOutput(new UserOutput(user));

    return authResponse;
  }
}
