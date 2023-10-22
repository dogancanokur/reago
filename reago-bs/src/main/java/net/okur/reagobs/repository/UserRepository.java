package net.okur.reagobs.repository;

import net.okur.reagobs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User findByActivationToken(String token);
  User findByEmail(String email);

  User findByUsername(String username);
}
