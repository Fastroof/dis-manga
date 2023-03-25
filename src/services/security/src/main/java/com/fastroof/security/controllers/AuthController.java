package com.fastroof.security.controllers;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.apricotka.orders.pojo.Mail;
import com.fastroof.security.models.ERole;
import com.fastroof.security.payload.request.SignupRequest;
import com.fastroof.security.payload.response.JwtResponse;
import com.fastroof.security.payload.response.MessageResponse;
import com.fastroof.security.repository.RoleRepository;
import com.fastroof.security.repository.UserRepository;
import com.fastroof.security.security.jwt.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.kafka.core.KafkaTemplate;
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
	private final KafkaTemplate<String, Mail> kafkaTemplate;

	@Autowired
	public AuthController(AuthenticationManager authenticationManager,
						  UserRepository userRepository,
						  RoleRepository roleRepository,
						  PasswordEncoder encoder,
						  JwtUtils jwtUtils, KafkaTemplate<String, Mail> kafkaTemplate) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.encoder = encoder;
		this.jwtUtils = jwtUtils;
		this.kafkaTemplate = kafkaTemplate;
	}

	@PostMapping("/signin")
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
			List<String> roles = userDetails.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList());

			return ResponseEntity.ok(new JwtResponse(jwt,
					userDetails.getId(),
					userDetails.getPIB(),
					userDetails.getUsername(),
					roles));
		} catch (Exception e) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Не правильний email або пароль"));
		}
	}

	@PostMapping("/signup")
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

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		sendMessage(user.getEmail(), "Вітаємо з успішною реєстрацією", "###reg###");

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@Value("${link.to.service.messenger}")
	private String linkToMessenger;
	@Value("${is.kafka}")
	private Boolean isKafka;
	private final RestTemplate restTemplate = new RestTemplate();
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	private static final String TOPIC = "auth";

	public void sendMessage(String mail, String title, String body) {
		if (isKafka) {
			Mail mail1 = new Mail(mail, title, body);
			logger.info(String.format("#### -> Producing message -> %s", mail1));
			this.kafkaTemplate.send(TOPIC, mail1);
		} else {
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
}
