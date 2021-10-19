package com.borgestech.securityrest.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.borgestech.securityrest.model.RoleModel;
import com.borgestech.securityrest.model.UserModel;
import com.borgestech.securityrest.repository.RoleRepository;
import com.borgestech.securityrest.repository.UserRepository;
import com.borgestech.securityrest.request.SigninRequest;
import com.borgestech.securityrest.request.SignupRequest;
import com.borgestech.securityrest.response.SigninResponse;
import com.borgestech.securityrest.security.jwt.JwtUtils;
import com.borgestech.securityrest.services.UserDetailsImpl;

@RestController
@RequestMapping("/api")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@PostMapping("/auth/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody SigninRequest signin) {
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(signin.getUsername(), signin.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		Set<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toSet());
		
		return ResponseEntity.ok(new SigninResponse(signin.getUsername(), roles, jwt));
	}
	
	@PostMapping("/auth/signup")
	public ResponseEntity<?> saveUser(@RequestBody SignupRequest signup) {
		
		if (userRepository.existsByUsername(signup.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body("Error: Username is already taken!");
		}

		if (userRepository.existsByEmail(signup.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body("Error: Email is already in use!");
		}
		
		UserModel user = new UserModel(signup.getUsername(), 
				signup.getEmail(),
				 encoder.encode(signup.getPassword()));
		
		RoleModel roleUser = new RoleModel();
		roleUser.setId(1);
		RoleModel roleAdmin = new RoleModel();
		roleAdmin.setId(2);
		user.setRoles(Set.of(roleUser, roleAdmin));
		
		userRepository.save(user);
		
		return ResponseEntity.ok("User registered successfully!");
	}
}
