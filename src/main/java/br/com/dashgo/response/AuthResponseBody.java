package br.com.dashgo.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseBody {

	private String email;
	private String accessToken;
	private String refreshToken;
	private String tokenType;
	private List<String> roles;
	private List<String> permissions;
	
}
