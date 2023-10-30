package net.okur.reagobs.service;

import net.okur.reagobs.dto.request.Credentials;
import net.okur.reagobs.dto.response.AuthResponse;
import net.okur.reagobs.token.Token;

public interface AuthService {
  AuthResponse authenticate(Credentials credentials);
}
