package br.com.dashgo.security.user;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import br.com.dashgo.model.User;
import br.com.dashgo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
    	log.info("Searching in the DB the user by username '{}'", username);
    	User user = userService.findByUsernameOrThrowNotFoundException(username);
        
        log.info("user found '{}'", user.getUsername());

        return CustomUserDetails.toCustomUserDetails(user);
    }

    public static final class CustomUserDetails extends User implements UserDetails {
		private static final long serialVersionUID = 1L;

        public static CustomUserDetails toCustomUserDetails(User other) {
            CustomUserDetails user = new CustomUserDetails();
            user.setUserId(other.getUserId());
            user.setEmail(other.getEmail());
            user.setUsername(other.getUsername());
            user.setPassword(other.getPassword());
            user.setRoles(other.getRoles());
            user.setPermissions(other.getPermissions());
            return user;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
        	return super.getRoles().stream().map(item -> new SimpleGrantedAuthority(item.getName())).collect(Collectors.toList());
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
            return true;
        }
    }
}
