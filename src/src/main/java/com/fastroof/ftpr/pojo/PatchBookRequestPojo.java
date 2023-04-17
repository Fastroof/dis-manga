package com.fastroof.ftpr.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

/**
 * The PatchBookRequestPojo Class.
 */
@Getter
@Setter
@ToString
public class PatchBookRequestPojo {
    
    /** The name. */
    private String name;
    
    /** The tag id. */
    @JsonProperty("tag_id")
    private Integer tagId;
    
    /** The book files. */
    private List<MultipartFile> files;
    
    /** The cover file. */
    @JsonProperty("cover_file")
    private MultipartFile coverFile;

    /**
     * Checks if is valid.
     *
     * @return true, if is valid
     */
    public boolean isValid() {
        return Objects.nonNull(name) || Objects.nonNull(tagId) || Objects.nonNull(files) || Objects.nonNull(coverFile);
    }
}
