package com.fastroof.ftpr.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PostBookRequest {
    @NotBlank
    private String name;
    private Integer tagId;
}
