package com.fastroof.security.controllers;

import java.util.*;
import java.util.stream.Collectors;
import javax.validation.Valid;
import com.fastroof.security.models.ERole;
import com.fastroof.security.payload.request.SignupRequest;
import com.fastroof.security.payload.request.ValidateTokenRequest;
import com.fastroof.security.payload.response.JwtResponse;
import com.fastroof.security.payload.response.MessageResponse;
import com.fastroof.security.repository.RoleRepository;
import com.fastroof.security.repository.UserRepository;
import com.fastroof.security.security.jwt.AuthTokenFilter;
import com.fastroof.security.security.jwt.JwtUtils;
import com.fastroof.security.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;
import com.fastroof.security.models.Role;
import com.fastroof.security.models.User;
import com.fastroof.security.payload.request.LoginRequest;
import com.fastroof.security.security.services.UserDetailsImpl;
import org.springframework.web.client.RestTemplate;

/**
 * The AuthController Class.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	/** The authentication manager. */
	private final AuthenticationManager authenticationManager;
	
	/** The user repository. */
	private final UserRepository userRepository;
	
	/** The role repository. */
	private final RoleRepository roleRepository;
	
	/** The encoder. */
	private final PasswordEncoder encoder;
	
	/** The jwt utils. */
	private final JwtUtils jwtUtils;
	
	/** The user details service. */
	private final UserDetailsServiceImpl userDetailsService;

	/**
	 * Instantiates a new auth controller.
	 *
	 * @param authenticationManager the authentication manager
	 * @param userRepository the user repository
	 * @param roleRepository the role repository
	 * @param encoder the encoder
	 * @param jwtUtils the jwt utils
	 * @param userDetailsService the user details service
	 */
	@Autowired
	public AuthController(AuthenticationManager authenticationManager,
						  UserRepository userRepository,
						  RoleRepository roleRepository,
						  PasswordEncoder encoder,
						  JwtUtils jwtUtils,
						  UserDetailsServiceImpl userDetailsService) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.encoder = encoder;
		this.jwtUtils = jwtUtils;
		this.userDetailsService = userDetailsService;
	}

	/**
	 * Authenticate user.
	 *
	 * @param loginRequest the login request
	 * @return the response entity
	 */
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

	/**
	 * Validate token.
	 *
	 * @param validateTokenRequest the validate token request
	 * @return the response entity
	 */
	@PostMapping("/validate")
	public ResponseEntity<UserDetails> validateToken(@Valid @RequestBody ValidateTokenRequest validateTokenRequest) {
		String jwt = validateTokenRequest.getToken();
		if (jwtUtils.validateJwtToken(jwt)) {
			String username = jwtUtils.getUserNameFromJwtToken(jwt);

			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			return ResponseEntity
					.ok(userDetails);
		} else {
			return ResponseEntity
					.status(403)
					.build();
		}
	}

	/**
	 * Register user.
	 *
	 * @param signUpRequest the sign-up request
	 * @return the response entity
	 */
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

	/** The link to messenger. */
	@Value("${link.to.service.messenger}")
	private String linkToMessenger;
	
	/** The rest template. */
	private final RestTemplate restTemplate = new RestTemplate();

	/**
	 * Helper method to send messages.
	 *
	 * @param mail the mail
	 * @param title the title
	 * @param body the body
	 */
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
