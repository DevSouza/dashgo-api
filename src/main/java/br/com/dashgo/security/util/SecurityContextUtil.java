package br.com.dashgo.security.util;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.dashgo.model.Permission;
import br.com.dashgo.model.Role;
import br.com.dashgo.model.User;
import br.com.dashgo.security.user.UserDetailsServiceImpl.CustomUserDetails;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Andre Souza Dos Santos
 */
@Slf4j
public class SecurityContextUtil {
    private SecurityContextUtil() {
    }

    @SuppressWarnings("unchecked")
    public static void setSecurityContext(Claims body) {
        try {
    		List<Role> roles = (List<Role>) body.get("roles", ArrayList.class).stream().map(item -> new Role(null, item.toString())).collect(Collectors.toList());
    		List<Permission> permissions = (List<Permission>) body.get("permissions", new ArrayList<String>().getClass()).stream().map(item -> new Permission(null, item.toString())).collect(Collectors.toList());
    		
    		CustomUserDetails user =  CustomUserDetails.toCustomUserDetails(User.builder()
    				.username(body.getSubject())
    				.email(body.get("email", String.class))
    				.userId(body.get("userId", Long.class))
    				.roles(new HashSet<>(roles))
    				.permissions(new HashSet<>(permissions))
    				.build());
    		
    		
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, createAuthorities(body.get("roles", new ArrayList<String>().getClass())));
            auth.setDetails(user);

            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception e) {
            log.error("Error setting security context ", e);
            SecurityContextHolder.clearContext();
        }
    }

    private static List<SimpleGrantedAuthority> createAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(toList());
    }
}
