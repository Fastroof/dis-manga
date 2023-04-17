//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.fastroof.ftpr.service.filestorage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * The UploadFileResponse Class.
 */
@JsonIgnoreProperties(
        ignoreUnknown = true
)
@Getter
@Setter
public class UploadFileResponse {
    
    /** The id. */
    private String id;
    
    /** The name. */
    private String name;
}
