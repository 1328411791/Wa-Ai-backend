package org.talang.sdk.models.options;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
{
  "resize_mode": 0,
  "show_extras_results": true,
  "gfpgan_visibility": 0.07745869113225834,
  "codeformer_visibility": 0.47970707311691596,
  "codeformer_weight": 0.3154859812234816,
  "upscaling_resize": 5.823972728481651,
  "upscaling_resize_w": 78222408,
  "upscaling_resize_h": 11201482,
  "upscaling_crop": false,
  "upscaler_1": "sed ipsum anim proident dolor",
  "upscaler_2": "proident occaecat",
  "extras_upscaler_2_visibility": 0.6957613714482722,
  "upscale_first": true,
  "image": "http://dummyimage.com/400x400"
}
 */
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
    @Builder.Default
    @JsonProperty("codeformer_visibility")
    private Double codeformerVisibility = 0.0;
    /**
     * CodeFormer Weight，Sets the weight of CodeFormer, values should be between 0 and 1.
     */
    @Builder.Default
    @JsonProperty("codeformer_weight")
    private Double codeformerWeight = 0.0;
    /**
     * Secondary upscaler visibility，Sets the visibility of secondary upscaler, values should be
     * between 0 and 1.
     */
    @Builder.Default
    @JsonProperty("extras_upscaler_2_visibility")
    private Double extrasUpscaler2Visibility = 0.0;
    /**
     * GFPGAN Visibility，Sets the visibility of GFPGAN, values should be between 0 and 1.
     */
    @Builder.Default
    @JsonProperty("gfpgan_visibility")
    private Double gfpganVisibility = 0.0;
    /**
     * Image，Image to work on, must be a Base64 string containing the image's data.
     */
    @Builder.Default
    @JsonProperty("image")
    private String image = null;
    /**
     * Resize Mode，Sets the resize mode: 0 to upscale by upscaling_resize amount, 1 to upscale
     * up to upscaling_resize_h x upscaling_resize_w.
     */
    @Builder.Default
    @JsonProperty("resize_mode")
    private Long resizeMode = 0L;
    /**
     * Show results，Should the backend return the generated image?
     */
    @Builder.Default
    @JsonProperty("show_results")
    private Boolean showExtrasResults = false;
    /**
     * Upscale first，Should the upscaler run before restoring faces?
     */
    @Builder.Default
    @JsonProperty("upscale_first")
    private Boolean upscaleFirst = false;
    /**
     * Main upscaler，The name of the main upscaler to use, it has to be one of this list:
     */
    @Builder.Default
    @JsonProperty("upscaler_1")
    private String upscaler1 = "";
    /**
     * Secondary upscaler，The name of the secondary upscaler to use, it has to be one of this
     * list:
     */
    @Builder.Default
    @JsonProperty("upscaler_2")
    private String upscaler2 = "";
    /**
     * Crop to fit，Should the upscaler crop the image to fit in the chosen size?
     */
    @Builder.Default
    @JsonProperty("upscaling_crop")
    private Boolean upscalingCrop = false;
    /**
     * Upscaling Factor，By how much to upscale the image, only used when resize_mode=0.
     */
    @Builder.Default
    @JsonProperty("upscaling_resize")
    private Double upscalingResize = 0.0;
    /**
     * Target Height，Target height for the upscaler to hit. Only used when resize_mode=1.
     */
    @Builder.Default
    @JsonProperty("upscaling_resize_h")
    private Long upscalingResizeH = 512L;
    /**
     * Target Width，Target width for the upscaler to hit. Only used when resize_mode=1.
     */
    @Builder.Default
    @JsonProperty("upscaling_resize_w")
    private Long upscalingResizeW = 512L;
}
