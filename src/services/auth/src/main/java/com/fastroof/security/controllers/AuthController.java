package com.fastroof.security.controllers;

import java.util.*;
import java.util.stream.Collectors;
import javax.validation.Valid;
import com.fastroof.security.models.ERole;
import com.fastroof.security.payload.request.SignupRequest;
import com.fastroof.security.payload.response.JwtResponse;
import com.fastroof.security.payload.response.MessageResponse;
import com.fastroof.security.repository.RoleRepository;
import com.fastroof.security.repository.UserRepository;
import com.fastroof.security.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fastroof.security.models.Role;
import com.fastroof.security.models.User;
import com.fastroof.security.payload.request.LoginRequest;
import com.fastroof.security.security.services.UserDetailsImpl;
import org.springframework.web.client.RestTemplate;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder encoder;
	private final JwtUtils jwtUtils;
	@Autowired
	public AuthController(AuthenticationManager authenticationManager,
						  UserRepository userRepository,
						  RoleRepository roleRepository,
						  PasswordEncoder encoder,
						  JwtUtils jwtUtils) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.encoder = encoder;
		this.jwtUtils = jwtUtils;
	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		if (!userRepository.existsByEmail(loginRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Не правильний email або пароль"));
		}
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			String role = userDetails.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList()).get(0);

			return ResponseEntity.ok(new JwtResponse(jwt,
					userDetails.getId(),
					userDetails.getPIB(),
					userDetails.getUsername(),
					role));
		} catch (Exception e) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Не правильний email або пароль"));
		}
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));

		Role role = roleRepository.findByName(ERole.user)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		user.setRole(role);
		user.setProvider("local");
		userRepository.save(user);
		// sendMessage(user.getEmail(), "Вітаємо з успішною реєстрацією", "###reg###");
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@Value("${link.to.service.messenger}")
	private String linkToMessenger;
	private final RestTemplate restTemplate = new RestTemplate();

	public void sendMessage(String mail, String title, String body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String, Object> map = new HashMap<>();
		map.put("recipient", mail);
		map.put("subject", title);
		map.put("msgBody", body);
		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
		ResponseEntity<String> response = this.restTemplate.postForEntity(linkToMessenger + "sendMail", entity, String.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			response.getBody();
		}
	}
}
