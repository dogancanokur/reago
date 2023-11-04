package net.okur.reagobs.repository;

import java.util.Optional;
import net.okur.reagobs.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User findByActivationToken(String token);

  User findByEmail(String email);

  Optional<User> findByUsername(String username);

  Page<User> findByIdNot(Long id, Pageable pageable);
}
