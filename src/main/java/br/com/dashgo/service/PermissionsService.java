package br.com.dashgo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.dashgo.model.Permission;
import br.com.dashgo.repository.PermissionRespository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionsService {
	
	private final PermissionRespository permissionRepository;

	public List<Permission> findAll() {
		return permissionRepository.findAll();
	}

}
