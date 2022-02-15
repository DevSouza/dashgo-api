package br.com.dashgo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.dashgo.model.RefreshToken;
import br.com.dashgo.model.User;
import br.com.dashgo.response.AuthResponseBody;
import br.com.dashgo.response.UserGetResponseBody;
import br.com.dashgo.security.user.UserDetailsServiceImpl.CustomUserDetails;
import br.com.dashgo.security.util.JWTUtils;

@Service
public class AuthService {
	@Autowired private AuthenticationManager authenticationManager;
	@Autowired private RefreshTokenService refreshTokenService;
	@Autowired private UserService userService;
	@Autowired private JWTUtils jwtUtils;
	
	public AuthResponseBody signIn(String username, String password) {
			
		// Find user.
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		User user = (User) authentication.getPrincipal();
		
		// create refreshToken
		RefreshToken refresh = refreshTokenService.createRefreshToken(user.getUserId());
		
		return createResponseAuth(user, refresh);
	}

	public AuthResponseBody refreshToken(String refreshToken) {
		RefreshToken oldRefreshToken = refreshTokenService.findByTokenOrThrowBadRequestException(refreshToken);
		User user = oldRefreshToken.getUser();
		refreshTokenService.verifyExpiration(oldRefreshToken);
		
		// create refreshToken and delete old 
		RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user.getUserId());
		refreshTokenService.delete(oldRefreshToken);
		
		return createResponseAuth(user, newRefreshToken);
	}

	private AuthResponseBody createResponseAuth(User user, RefreshToken refresh) {
		String email = user.getEmail();
		String accessToken = jwtUtils.createTokenJwt(user);
		String refreshToken = refresh.getToken(); 
		List<String> roles = user.getListRoles();
		List<String> permissions = user.getListPermissions();
		
		return AuthResponseBody.builder()
				.email(email)
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.roles(roles)
				.permissions(permissions)
				.tokenType(jwtUtils.getTokenType())
				.build();
	}
	
	public UserGetResponseBody userInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = ((CustomUserDetails) authentication.getPrincipal()).getUsername();
		
		User user = userService.findByUsernameOrThrowNotFoundException(username);
		
		return UserGetResponseBody.builder()
				.username(username)
				.email(user.getEmail())
				.roles(user.getListRoles())
				.permissions(user.getListPermissions())
				.build();
	}
}
