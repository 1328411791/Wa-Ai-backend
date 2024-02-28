package org.talang.sdk.models.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpscalersResult {
    /**
     * Model Name
     */
    @JsonProperty("model_name")
    private String modelName;
    /**
     * Path
     */
    @JsonProperty("model_path")
    private String modelPath;
    /**
     * URL
     */
    @JsonProperty("model_url")
    private String modelurl;
    /**
     * Name
     */
    @JsonProperty("name")
    private String name;
    /**
     * Scale
     */
    @JsonProperty("scale")
    private Integer scale;
}
