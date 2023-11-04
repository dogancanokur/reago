package net.okur.reagobs.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Value;

/** DTO for {@link net.okur.reagobs.entity.User} */
@Value
public class UserInput implements Serializable {
  @NotNull Long id;

  @NotBlank(message = "{warning.user.username.not-blank}")
  @Size(message = "{warning.user.username.length}", min = 3, max = 25)
  String username;
}
