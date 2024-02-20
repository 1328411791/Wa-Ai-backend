package org.talang.wabackend.sd;

import org.talang.wabackend.sd.model.ExtraImageOptions;
import org.talang.wabackend.sd.model.ExtraImageResult;

public interface ExtraImage {

    ExtraImageResult txt2Img(ExtraImageOptions options);
}
