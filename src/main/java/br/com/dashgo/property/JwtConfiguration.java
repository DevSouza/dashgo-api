package br.com.dashgo.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Andre Souza
 */
@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties(prefix = "jwt.config")
public class JwtConfiguration {
    
	private String tokenType = "Bearer";
	private String secret = "DEFAULT_SECRET"; 
	private int expirationAccessToken = 1000 * 60 * 30; // 30 minutes 
	private int expirationRefreshToken = 1000 * 60 * 60; // 60 minutes

}
