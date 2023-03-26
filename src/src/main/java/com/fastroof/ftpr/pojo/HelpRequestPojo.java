package com.fastroof.ftpr.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class HelpRequestPojo {
    @NotBlank
    private String email;
    @NotBlank
    private String text;
}
