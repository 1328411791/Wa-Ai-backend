package org.talang.wabackend.model.dto.sd;

import lombok.Data;
import org.talang.sdk.models.options.ExtraImageOptions;
import org.talang.sdk.models.options.Txt2ImageOptions;

@Data
public class ExtraImageDto {

    private Txt2ImageOptions txt2ImageOptions;

    private ExtraImageOptions extraImageOptions;
}
