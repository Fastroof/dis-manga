//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.fastroof.ftpr.service.filestorage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
@Getter
@Setter
public class UploadImageResponse {
    private UploadImageData data;
    private Boolean success;
    private Integer status;
}
