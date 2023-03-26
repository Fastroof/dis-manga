package com.fastroof.ftpr.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class HelpRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String text;
}
