package com.fastroof.security.payload.response;

import lombok.Getter;
import lombok.Setter;

/**
 * The JwtResponse Class.
 */
@Getter
@Setter
public class JwtResponse {
	
	/** The token. */
	private String token;
	
	/** The type. */
	private String type = "Bearer";
	
	/** The id. */
	private Long id;
	
	/** The username. */
	private String username;
	
	/** The email. */
	private String email;
	
	/** The role. */
	private String role;

	/**
	 * Instantiates a new jwt response.
	 *
	 * @param accessToken the access token
	 * @param id the id
	 * @param username the username
	 * @param email the email
	 * @param role the role
	 */
	public JwtResponse(String accessToken, Long id, String username, String email,  String role) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.role = role;
	}
}
