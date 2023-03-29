package com.fastroof.security.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ValidateTokenRequest {
    @NotBlank
    private String token;
}
