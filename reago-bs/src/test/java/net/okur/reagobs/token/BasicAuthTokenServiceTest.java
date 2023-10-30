package net.okur.reagobs.token;

import net.okur.reagobs.dto.request.Credentials;
import net.okur.reagobs.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
class BasicAuthTokenServiceTest {

  BasicAuthTokenService basicAuthTokenService = new BasicAuthTokenService();

  @Test
  void createToken() {
    Credentials creds = new Credentials("test@mail", "testpassword");
    User user = new User();
    user.setPassword("testpassword");
    user.setEmail("test@mail");
    Token token = basicAuthTokenService.createToken(user, creds);
    Assertions.assertEquals(token.prefix(), "Basic");
    Assertions.assertEquals(token.token(), "dGVzdEBtYWlsOnRlc3RwYXNzd29yZA==");

  }

  @Test
  void verifyToken() {
    User user = basicAuthTokenService.verifyToken("dGVzdEBtYWlsOnRlc3RwYXNzd29yZA==");

  }
}