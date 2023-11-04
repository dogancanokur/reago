package net.okur.reagobs.dto.output;

import java.io.Serializable;
import lombok.Data;
import net.okur.reagobs.entity.User;
import org.springframework.util.StringUtils;

/**
 * DTO for {@link net.okur.reagobs.entity.User}
 */
@Data
public class UserOutput implements Serializable {
  Long id;
  String username;
  String email;
  String image;
  String name;

  public UserOutput(User user) {
    setId(user.getId());
    setUsername(user.getUsername());
    setEmail(user.getEmail());
    setName(user.getName());
    setImage(user.getImage());
  }

  public String getImage() {
    return StringUtils.hasText(image) ? image : "default.png";
  }
}