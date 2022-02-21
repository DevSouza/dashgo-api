package br.com.dashgo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "permissions")
@JsonInclude(Include.NON_NULL)
public class Permission {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer permissionId;

	@Column(length = 20)
	private String name;
	
	@Column(length = 255)
	private String description;
	
	@Default
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="permission_has_roles_by_default", 
    	joinColumns={@JoinColumn(name="permissionId")}, 
    	inverseJoinColumns={@JoinColumn(name="roleId")})
	private Set<Role> defaultRoles = new HashSet<>();
	
	public Permission(Integer permissionId, String name) {
		this.permissionId = permissionId;
		this.name = name;
	}

}