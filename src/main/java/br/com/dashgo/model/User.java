package br.com.dashgo.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@JsonInclude(Include.NON_NULL)
@Table(	name = "users", 
uniqueConstraints = { 
		@UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email") 
})
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@JsonIgnore
	private String password;
	
	@Default
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_has_roles", 
    	joinColumns={@JoinColumn(name="userId")}, 
    	inverseJoinColumns={@JoinColumn(name="roleId")})
	private Set<Role> roles = new HashSet<>();
	
	@Default
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_has_permissions", 
    	joinColumns={@JoinColumn(name="userId")}, 
    	inverseJoinColumns={@JoinColumn(name="permissionId")})
	private Set<Permission> permissions = new HashSet<>();

	private LocalDateTime createdAt;
	

	@Transient
	@JsonIgnore
	public List<String> getListRoles() {
		return roles.stream().map(Role::getName).collect(Collectors.toList());
	}
	
	@Transient
	@JsonIgnore
	public List<String> getListPermissions() {
		return permissions.stream().map(Permission::getName).collect(Collectors.toList());
	}
}
