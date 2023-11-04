package net.okur.reagobs.configuration;

import net.okur.reagobs.entity.User;
import net.okur.reagobs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {
  private final UserService userService;

  @Autowired
  public AppUserDetailsService(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user =
        userService
            .findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(email + " not found"));

    return new CurrentUser(user);
  }
}
