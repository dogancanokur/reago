package net.okur.reagobs.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.okur.reagobs.dto.output.UserOutput;
import net.okur.reagobs.token.Token;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponse {

  Token token;
  UserOutput userOutput;
}
