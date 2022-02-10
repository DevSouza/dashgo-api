package br.com.dashgo.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserGetResponseBody {
	
	private String username;
	private String email;
	private List<String> roles;
	private List<String> permissions;
	
}
