package br.com.dashgo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
	
	private Integer roleId;
	private String name;
	private String decription;
	
}
