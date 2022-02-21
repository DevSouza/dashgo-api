package br.com.dashgo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.dashgo.exception.NotFoundException;
import br.com.dashgo.model.Permission;
import br.com.dashgo.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionsService {
	
	private final PermissionRepository permissionRepository;

	public List<Permission> findAll() {
		return permissionRepository.findAll();
	}

	public Permission findByPermissionIdOrThrowNotFoundException(Integer permissionId) {
		return permissionRepository.findById(permissionId).orElseThrow(() -> new NotFoundException("Permissão não encontrada!"));
	}

}
