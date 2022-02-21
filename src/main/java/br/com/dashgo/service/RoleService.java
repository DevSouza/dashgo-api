package br.com.dashgo.service;

import org.springframework.stereotype.Service;

import br.com.dashgo.exception.NotFoundException;
import br.com.dashgo.model.Role;
import br.com.dashgo.repository.RoleRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {
	
	private final RoleRepository roleRepository;

	public Role findByRoleIdOrThrowNotFoundException(Integer roleId) {
		return roleRepository.findById(roleId).orElseThrow(() -> new NotFoundException("Permissão não encontrada!"));
	}

}
