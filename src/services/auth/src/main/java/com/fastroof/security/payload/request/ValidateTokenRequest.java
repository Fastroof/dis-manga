package com.fastroof.security.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * The ValidateTokenRequest Class.
 */
@Getter
@Setter
public class ValidateTokenRequest {
    
    /** The token. */
    @NotBlank
    private String token;
}
