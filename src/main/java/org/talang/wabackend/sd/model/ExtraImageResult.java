package org.talang.wabackend.sd.model;

import lombok.Data;

@Data
public class ExtraImageResult {
    /**
     * HTML info，A series of HTML tags containing the process info.
     */
    private String htmlInfo;
    /**
     * Image，The generated image in base64 format.
     */
    private String image;
}
