package org.talang.sdk;

import org.talang.sdk.models.options.Txt2ImageOptions;
import org.talang.sdk.models.results.Txt2ImgResult;

public interface Txt2Image {

  /**
   * Generate images from text.
   *
   * @param options The options to generate images.
   * @return The result of the generation.
   *
   * @throws org.talang.sdk.exceptions.SdWebuiServerValidationException
   * @throws org.talang.sdk.exceptions.SdWebuiBadRequestException
   */
  Txt2ImgResult txt2Img(Txt2ImageOptions options);

}
