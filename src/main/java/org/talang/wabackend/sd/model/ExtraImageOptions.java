package org.talang.wabackend.sd.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExtraImageOptions {
    /**
     * CodeFormer Visibility，Sets the visibility of CodeFormer, values should be between 0 and 1.
     */
    private Double codeformerVisibility;
    /**
     * CodeFormer Weight，Sets the weight of CodeFormer, values should be between 0 and 1.
     */
    private Double codeformerWeight;
    /**
     * Secondary upscaler visibility，Sets the visibility of secondary upscaler, values should be
     * between 0 and 1.
     */
    private Double extrasUpscaler2_Visibility;
    /**
     * GFPGAN Visibility，Sets the visibility of GFPGAN, values should be between 0 and 1.
     */
    private Double gfpganVisibility;
    /**
     * Image，Image to work on, must be a Base64 string containing the image's data.
     */
    private String image;
    /**
     * Resize Mode，Sets the resize mode: 0 to upscale by upscaling_resize amount, 1 to upscale
     * up to upscaling_resize_h x upscaling_resize_w.
     */
    private Long resizeMode;
    /**
     * Show results，Should the backend return the generated image?
     */
    private Boolean showExtrasResults;
    /**
     * Upscale first，Should the upscaler run before restoring faces?
     */
    private Boolean upscaleFirst;
    /**
     * Main upscaler，The name of the main upscaler to use, it has to be one of this list:
     */
    private String upscaler1;
    /**
     * Secondary upscaler，The name of the secondary upscaler to use, it has to be one of this
     * list:
     */
    private String upscaler2;
    /**
     * Crop to fit，Should the upscaler crop the image to fit in the chosen size?
     */
    private Boolean upscalingCrop;
    /**
     * Upscaling Factor，By how much to upscale the image, only used when resize_mode=0.
     */
    private Double upscalingResize;
    /**
     * Target Height，Target height for the upscaler to hit. Only used when resize_mode=1.
     */
    private Long upscalingResizeH;
    /**
     * Target Width，Target width for the upscaler to hit. Only used when resize_mode=1.
     */
    private Long upscalingResizeW;
}
