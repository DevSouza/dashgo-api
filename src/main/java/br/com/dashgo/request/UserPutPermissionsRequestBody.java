package br.com.dashgo.request;

import java.util.List;

import br.com.dashgo.model.dto.PermissionDTO;
import br.com.dashgo.model.dto.RoleDTO;
import lombok.Data;

@Data
public class UserPutPermissionsRequestBody {
	
	private List<PermissionDTO> permissions;
	private List<RoleDTO> roles;
	
}
