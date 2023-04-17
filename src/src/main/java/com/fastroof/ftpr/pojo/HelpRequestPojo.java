package com.fastroof.ftpr.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * The HelpRequestPojo Class.
 */
@Getter
@Setter
public class HelpRequestPojo {
    
    /** The email. */
    @NotBlank
    private String email;
    
    /** The text. */
    @NotBlank
    private String text;
}
