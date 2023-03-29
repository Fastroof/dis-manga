package com.fastroof.ftpr.service.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class ValidateTokenRequest {
    @NotBlank
    private String token;
}
