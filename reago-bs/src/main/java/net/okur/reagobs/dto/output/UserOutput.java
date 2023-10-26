package net.okur.reagobs.dto.output;

import lombok.Data;
import net.okur.reagobs.entity.User;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * DTO for {@link net.okur.reagobs.entity.User}
 */
@Data
public class UserOutput implements Serializable {
  Long id;
  String username;
  String email;
  String image;
  String fullName;

  public UserOutput(User user) {
    setId(user.getId());
    setUsername(user.getUsername());
    setEmail(user.getEmail());
    setFullName((StringUtils.hasText(user.getFirstName()) ? user.getFirstName() : "") + " " + (StringUtils.hasText(
        user.getLastName()) ? user.getLastName() : ""));
    setImage(user.getImage());
  }

  public String getImage() {
    return StringUtils.hasText(image) ? image : "default.png";
  }
}