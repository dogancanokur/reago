package net.okur.reagobs.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long id;

  @Size(message = "{warning.user.username.length}", min = 3, max = 25)
  @Column(name = "username", nullable = false, unique = true, length = 25)
  private String username;

  @Email(message = "{warning.user.email.not-valid}")
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Size(message = "{warning.user.password.length}", min = 8, max = 16)
  @Pattern(message = "{warning.user.password.pattern}", regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=./\\d).*$")
  @Column(name = "password", nullable = false)
  private String password;

}