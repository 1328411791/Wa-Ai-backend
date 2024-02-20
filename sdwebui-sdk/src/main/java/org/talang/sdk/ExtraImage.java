package org.talang.sdk;


import org.talang.sdk.models.options.ExtraImageOptions;
import org.talang.sdk.models.results.ExtraImageResult;

public interface ExtraImage {

    ExtraImageResult extraImage(ExtraImageOptions options);
}
