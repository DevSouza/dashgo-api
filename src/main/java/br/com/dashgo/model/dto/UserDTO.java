package br.com.dashgo.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	
	private Long userId;
	private String username;
	private String email;
	private String password;
	private List<RoleDTO> roles;
	private List<PermissionDTO> permissions;
	private LocalDateTime createdAt;
	
}
