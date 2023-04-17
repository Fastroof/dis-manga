package com.fastroof.ftpr.service.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * The ValidateTokenRequest Class.
 */
@Getter
@Setter
@AllArgsConstructor
public class ValidateTokenRequest {
    
    /** The token. */
    @NotBlank
    private String token;
}
