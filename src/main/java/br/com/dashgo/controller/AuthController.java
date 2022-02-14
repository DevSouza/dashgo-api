package br.com.dashgo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dashgo.request.RefreshTokenPostRequestBody;
import br.com.dashgo.request.SignInPostRequestBody;
import br.com.dashgo.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired private AuthService authService;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignInPostRequestBody body) {
		return ResponseEntity.ok(authService.signIn(body.getUsername(), body.getPassword())); 
	}
	
	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(@Valid @RequestBody RefreshTokenPostRequestBody request) {
		return ResponseEntity.ok(authService.refreshToken(request.getRefreshToken()));
	}
	
	@GetMapping("/me")
	public ResponseEntity<?> infoUser() {		
		return ResponseEntity.ok(authService.userInfo());
	}
		
	/*@PostMapping("/logout")
	public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) {
		refreshTokenService.deleteByUserId(logOutRequest.getUserId());
		return ResponseEntity.ok(new MessageResponse("Log out successful!"));
	}*/

}
