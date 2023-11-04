package net.okur.reagobs.token;

import net.okur.reagobs.dto.request.Credentials;
import net.okur.reagobs.entity.User;
import net.okur.reagobs.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@AutoConfigureMockMvc
class BasicAuthTokenServiceTest {

  //  BasicAuthTokenService basicAuthTokenService = new BasicAuthTokenService(UserService);

  @Test
  void createToken() {
    //    Credentials creds = new Credentials("test@mail", "testpassword");
    //    User user = new User();
    //    user.setPassword("testpassword");
    //    user.setEmail("test@mail");
    //    Token token = basicAuthTokenService.createToken(user, creds);
    //    Assertions.assertEquals(token.prefix(), "Basic");
    //    Assertions.assertEquals(token.token(), "dGVzdEBtYWlsOnRlc3RwYXNzd29yZA==");

  }

  @Test
  void verifyToken() {
    //    User user = basicAuthTokenService.verifyToken("dGVzdEBtYWlsOnRlc3RwYXNzd29yZA==");

  }
}
