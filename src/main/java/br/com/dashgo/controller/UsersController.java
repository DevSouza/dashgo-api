package br.com.dashgo.controller;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;
import br.com.dashgo.model.User;
import br.com.dashgo.model.dto.PermissionDTO;
import br.com.dashgo.model.dto.RoleDTO;
import br.com.dashgo.model.dto.UserDTO;
import br.com.dashgo.request.UserPostRequestBody;
import br.com.dashgo.request.UserPutPermissionsRequestBody;
import br.com.dashgo.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UsersController {

	private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('users.list')")
    public ResponseEntity<?> listUsers(@PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(userService.findAll(pageable));
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('users.list')")
    public ResponseEntity<?> listUsers(@PathVariable("id") Long userId) {
    	User user = userService.findByUserIdOrThrowNotFoundException(userId);
    	UserDTO userDTO = new UserDTO();
    	BeanUtils.copyProperties(user, userDTO);
    	
    	userDTO.setPermissions(user.getPermissions().stream().map(item -> new PermissionDTO(item.getPermissionId(), item.getName(), item.getDescription())).collect(Collectors.toList()));
    	userDTO.setRoles(user.getRoles().stream().map(item -> new RoleDTO(item.getRoleId(), item.getName(), item.getDescription())).collect(Collectors.toList()));
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('users.create')")
    public ResponseEntity<?> postUser(@RequestBody @Valid UserPostRequestBody data) {
    	User user = userService.addUser(data);
    	return ResponseEntity.status(HttpStatus.CREATED).body(user);    	
    }
    
    @PutMapping("/{id}/permissions")
    @PreAuthorize("hasAuthority('users.update.permissions')")
    public ResponseEntity<?> putUserPermissions(@PathVariable("id") Long userId, @RequestBody UserPutPermissionsRequestBody body) {
    	// TODO: Adicionar regra que o userId 1 não pode ser alterado.
    	userService.updatePermissions(userId, body);    	
    	return ResponseEntity.noContent().build();
    }
}
