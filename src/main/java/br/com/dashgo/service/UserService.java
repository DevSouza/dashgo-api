package br.com.dashgo.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.dashgo.exception.BadRequestException;
import br.com.dashgo.model.User;
import br.com.dashgo.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}
	
	public User findByUserIdOrThrowBadRequestException(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new BadRequestException("User not found!"));
	}
	
	public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
	
	public User findByUsernameOrThrowBadRequestException(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new BadRequestException("User not found!"));
    }
}
