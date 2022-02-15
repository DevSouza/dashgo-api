package br.com.dashgo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.dashgo.exception.BadRequestException;
import br.com.dashgo.exception.NotFoundException;
import br.com.dashgo.exception.ValidationError;
import br.com.dashgo.model.User;
import br.com.dashgo.repository.UserRepository;
import br.com.dashgo.request.UserPostRequestBody;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}
	
	public User findByUserIdOrThrowNotFoundException(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Usuário não encontrado!"));
	}
	
	public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
	
	public User findByUsernameOrThrowNotFoundException(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Usuário não encontrado!"));
    }

	public User addUser(@Valid UserPostRequestBody data) {
		
		List<ValidationError> inner = new ArrayList<>();
		Optional<User> userByUsername = userRepository.findByUsername(data.getUsername());
		Optional<User> userByEmail = userRepository.findByEmail(data.getEmail());

		if(!data.getPassword().equals(data.getPasswordConfirmation()))
			inner.add(new ValidationError("","confirmPassword", "Confirmação de senha e a senha, não são iguais."));
		if(userByUsername.isPresent())
			inner.add(new ValidationError(data.getUsername(), "username", "Username indisponível."));		
		if(userByEmail.isPresent())
			inner.add(new ValidationError(data.getEmail(), "email", "E-mail indisponível."));
			
		if(!inner.isEmpty())
			throw new BadRequestException("Bad Request Exception, Invalid Fields", inner);
			
		User user = new User();
		user.setCreatedAt(LocalDateTime.now());
		BeanUtils.copyProperties(data, user);
		
		return userRepository.save(user);
	}
}
