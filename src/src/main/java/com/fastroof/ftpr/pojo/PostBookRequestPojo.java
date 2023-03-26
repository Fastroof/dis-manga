package com.fastroof.ftpr.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class PostBookRequestPojo {
    @NotBlank
    private String name;
    @JsonProperty("tag_id")
    private Integer tagId;
    @NotEmpty
    private List<MultipartFile> files;
    @JsonProperty("cover_file")
    private MultipartFile coverFile;
}
