package net.okur.reagobs.entity;

import jakarta.persistence.*;
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
  @Column(name = "username", nullable = false, unique = true, length = 25)
  private String username;
  @Column(name = "email", nullable = false, unique = true)
  private String email;
  @Column(name = "password", nullable = false)
  private String password;
}