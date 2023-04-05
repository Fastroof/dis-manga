package com.fastroof.ftpr.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
public class PatchBookRequestPojo {
    private String name;
    @JsonProperty("tag_id")
    private Integer tagId;
    private List<MultipartFile> files;
    @JsonProperty("cover_file")
    private MultipartFile coverFile;

    public boolean isValid() {
        return Objects.nonNull(name) || Objects.nonNull(tagId) || Objects.nonNull(files) || Objects.nonNull(coverFile);
    }
}
