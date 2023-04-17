//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.fastroof.ftpr.service.filestorage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * The UploadImageResponse Class.
 */
@JsonIgnoreProperties(
        ignoreUnknown = true
)
@Getter
@Setter
public class UploadImageResponse {
    
    /** The data. */
    private UploadImageData data;
    
    /** The success. */
    private Boolean success;
    
    /** The status. */
    private Integer status;
}
