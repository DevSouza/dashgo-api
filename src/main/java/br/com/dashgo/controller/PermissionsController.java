package br.com.dashgo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dashgo.service.PermissionsService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("permissions")
public class PermissionsController {

	private final PermissionsService permissionsService;
	
	@GetMapping
    //@PreAuthorize("hasAuthority('permissions.list')") // TODO: Configurar role permission.list
    public ResponseEntity<?> listPermissions() {
		return ResponseEntity.ok(permissionsService.findAll());
    }
	
}
