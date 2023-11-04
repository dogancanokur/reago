package net.okur.reagobs.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import net.okur.reagobs.entity.User;
import net.okur.reagobs.entity.validation.UniqueEmail;
import net.okur.reagobs.entity.validation.UniqueUsername;

/**
 * DTO for {@link net.okur.reagobs.entity.User}
 */
public record UserSaveInput(
    @UniqueUsername @Size(message = "{warning.user.username.length}", min = 3, max = 25) String username,
    @UniqueEmail @NotBlank @Email(message = "{warning.user.email.not-valid}") String email,
    @Size(message = "{warning.user.password.length}", min = 8, max = 255) //
    @Pattern(message = "{warning.user.password.pattern}", regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$") String password)
    implements Serializable {
  public User toUser() {
    User user = new User();
    user.setUsername(username);
    user.setEmail(email);
    user.setPassword(password);
    return user;
  }
}