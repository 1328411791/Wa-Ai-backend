package org.talang.sdk.models.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtraImageResult {
    /**
     * HTML info，A series of HTML tags containing the process info.
     */
    @JsonProperty("html_info")
    private String htmlInfo;
    /**
     * Image，The generated image in base64 format.
     */
    @JsonProperty("image")
    private String image;
}
