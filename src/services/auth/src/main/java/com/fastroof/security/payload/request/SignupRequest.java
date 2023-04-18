package com.fastroof.security.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import javax.validation.constraints.*;

/**
 * The SignupRequest Class.
 */
@Getter
@Setter
public class SignupRequest {
    
    /** The username. */
    @NotBlank
    @Size(min = 3)
    private String username;
 
    /** The email. */
    @NotBlank
    @Size(max = 100)
    @Email
    private String email;

    /** The password. */
    @NotBlank
    @Size(min = 8, max = 100)
    private String password;
}
