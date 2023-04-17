package com.fastroof.security.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * The LoginRequest Class.
 */
@Getter
@Setter
public class LoginRequest {
	
	/** The email. */
	@NotBlank
	private String email;

	/** The password. */
	@NotBlank
	private String password;
}
