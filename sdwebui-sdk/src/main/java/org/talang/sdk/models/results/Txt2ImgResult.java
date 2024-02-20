package org.talang.sdk.models.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.talang.sdk.models.options.Txt2ImageOptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Txt2ImgResult {

  @JsonProperty("images")
  private List<String> images;

  @JsonProperty("parameters")
  private Txt2ImageOptions parameters;

  @JsonProperty("info")
  private String info;

}
