package br.com.dashgo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDTO {
	
	private Integer permissionId;
	private String name;
	private String description;
	
}
