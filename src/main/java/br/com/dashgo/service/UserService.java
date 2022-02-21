package br.com.dashgo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.dashgo.exception.BadRequestException;
import br.com.dashgo.exception.NotFoundException;
import br.com.dashgo.exception.ValidationError;
import br.com.dashgo.model.Permission;
import br.com.dashgo.model.Role;
import br.com.dashgo.model.User;
import br.com.dashgo.repository.UserRepository;
import br.com.dashgo.request.UserPostRequestBody;
import br.com.dashgo.request.UserPutPermissionsRequestBody;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final PermissionsService permissionsService;
	private final RoleService roleService;
	
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
			
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		data.setPassword(encoder.encode(data.getPassword()));
		
		User user = new User();
		user.setCreatedAt(LocalDateTime.now());
		
		BeanUtils.copyProperties(data, user);
		
		return userRepository.save(user);
	}

	public void updatePermissions(Long userId, UserPutPermissionsRequestBody body) {
		User user = this.findByUserIdOrThrowNotFoundException(userId);
		
		user.getPermissions().clear();
		body.getPermissions().forEach(item -> {
			Permission permission = permissionsService.findByPermissionIdOrThrowNotFoundException(item.getPermissionId());
			user.getPermissions().add(permission);
		});
		
		user.getRoles().clear();
		body.getRoles().forEach(item -> {
			Role role = roleService.findByRoleIdOrThrowNotFoundException(item.getRoleId());
			user.getRoles().add(role);
		});
		
		userRepository.save(user);
	}
}
