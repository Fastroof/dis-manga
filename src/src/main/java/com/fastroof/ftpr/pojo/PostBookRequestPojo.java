package com.fastroof.ftpr.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * The PostBookRequestPojo Class.
 */
@Getter
@Setter
public class PostBookRequestPojo {
    
    /** The name. */
    @NotBlank
    private String name;
    
    /** The tag id.(Optional) */
    @JsonProperty("tag_id")
    private Integer tagId;
    
    /** The book files. */
    @NotEmpty
    private List<MultipartFile> files;
    
    /** The cover file.(Optional) */
    @JsonProperty("cover_file")
    private MultipartFile coverFile;
}
