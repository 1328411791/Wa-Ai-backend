package org.talang.sdk;

import org.talang.sdk.models.options.Image2ImageOptions;
import org.talang.sdk.models.results.Image2ImageResult;

public interface Image2Image {

  Image2ImageResult img2img(Image2ImageOptions options);

}
