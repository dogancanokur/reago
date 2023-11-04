package net.okur.reagobs.service;

import net.okur.reagobs.dto.request.Credentials;
import net.okur.reagobs.dto.response.AuthResponse;

public interface AuthService {
  AuthResponse authenticate(Credentials credentials);
}
