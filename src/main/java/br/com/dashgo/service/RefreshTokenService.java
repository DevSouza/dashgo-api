package br.com.dashgo.service;

import java.time.Instant;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dashgo.exception.BadRequestException;
import br.com.dashgo.model.RefreshToken;
import br.com.dashgo.property.JwtConfiguration;
import br.com.dashgo.repository.RefreshTokenRepository;
import br.com.dashgo.repository.UserRepository;

@Service
public class RefreshTokenService {

	@Autowired
	private JwtConfiguration jwtConfiguration;
	
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private UserRepository userRepository;

	public RefreshToken findByTokenOrThrowBadRequestException(String token) {
		return refreshTokenRepository.findByToken(token)
				.orElseThrow(() -> new BadRequestException("Refresh token is not in database!"));
	}

	public RefreshToken createRefreshToken(Long userId) {
		RefreshToken refreshToken = new RefreshToken();

		refreshToken.setUser(userRepository.findById(userId).get());
		refreshToken.setExpiryDate(Instant.now().plusMillis(jwtConfiguration.getExpirationRefreshToken()));
		refreshToken.setToken(UUID.randomUUID().toString());

		refreshToken = refreshTokenRepository.save(refreshToken);
		return refreshToken;
	}

  public RefreshToken verifyExpiration(RefreshToken token) {
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(token);
      throw new BadRequestException("Refresh token was expired. Please make a new signin request");
    }

    return token;
  }

  @Transactional
  public int deleteByUserId(Long userId) {
    return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
  }
  
  @Transactional
  public void delete(RefreshToken refreshToken) {
	  refreshTokenRepository.deleteById(refreshToken.getRefreshTokenId());
  }
  
}
