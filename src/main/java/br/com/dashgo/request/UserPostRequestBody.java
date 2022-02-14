package br.com.dashgo.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserPostRequestBody {

	@NotBlank(message = "Username not found")
	private String username;
	
	@Email(message = "Email not valid")
	@NotBlank(message = "Email not found")
	private String email;

	@NotBlank(message = "Password not found")
	private String password;
	
	@NotBlank(message = "Password confirmation not found")
	private String passwordConfirmation;
	
}
