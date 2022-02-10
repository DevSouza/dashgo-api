package br.com.dashgo.security.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dashgo.model.User;
import br.com.dashgo.property.JwtConfiguration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JWTUtils {
	
	private final JwtConfiguration jwtConfiguration;
	
	public String createTokenJwt(User user) {
		log.info("Starting to create the JWT");
		return Jwts.builder()
				.setSubject(user.getUsername())
				.claim("email", user.getEmail())
				.claim("userId", user.getUserId())
				.claim("roles", user.getListRoles())
				.claim("permissions", user.getListPermissions())
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtConfiguration.getExpirationAccessToken()))
				.signWith(SignatureAlgorithm.HS512, jwtConfiguration.getSecret())
				.compact();
	}
	
	public Claims getClaims(String token) throws SignatureException, MalformedJwtException, ExpiredJwtException, UnsupportedJwtException, IllegalArgumentException {
		return Jwts.parser().setSigningKey(jwtConfiguration.getSecret()).parseClaimsJws(token).getBody();
	}
	
	public String getTokenType() {
		return jwtConfiguration.getTokenType();
	}

}
