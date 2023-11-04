package net.okur.reagobs.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTests {

  @Autowired private MockMvc mockMvc;

  @Test
  void testHandleAuthentication_Positive() throws Exception {
    String requestBody = "{\"email\":\"testuser\",\"password\":\"testpassword\"}";

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  @Test
  void testHandleAuthentication_Negative() throws Exception {
    String requestBody = "{\"email\":\"user_1@reago.com\",\"password\":\"P4ssw0rd\"}";

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }
}
