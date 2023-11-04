package net.okur.reagobs.configuration;

import java.util.Collection;
import net.okur.reagobs.entity.User;
import net.okur.reagobs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
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
    User user = userService.findByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException(email + " not found");
    }

    return new UserDetails() {
      @Override
      public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("ROLE_USER");
      }

      @Override
      public String getPassword() {
        return user.getPassword();
      }

      @Override
      public String getUsername() {
        return user.getUsername();
      }

      @Override
      public boolean isAccountNonExpired() {
        return true;
      }

      @Override
      public boolean isAccountNonLocked() {
        return true;
      }

      @Override
      public boolean isCredentialsNonExpired() {
        return true;
      }

      @Override
      public boolean isEnabled() {
        return Boolean.TRUE.equals(user.getActive());
      }
    };
  }
}
