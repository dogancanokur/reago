package net.okur.reagobs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = { "email" }))
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "username", nullable = false, length = 25)
  private String username;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @JsonIgnore
  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "activation_token")
  private String activationToken;

  @Column(name = "active")
  private Boolean active = Boolean.FALSE;

  @Column(name = "image")
  private String image;

}