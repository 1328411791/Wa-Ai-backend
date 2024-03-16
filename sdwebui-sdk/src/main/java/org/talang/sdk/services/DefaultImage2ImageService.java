package org.talang.sdk.services;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.talang.sdk.Image2Image;
import org.talang.sdk.SdWebuiBeanContainer;
import org.talang.sdk.exceptions.SdBusinessException;
import org.talang.sdk.models.SdWebuiOptions;
import org.talang.sdk.models.options.Image2ImageOptions;
import org.talang.sdk.models.results.Image2ImageResult;
import org.talang.sdk.utils.JsonUtils;
import org.talang.sdk.utils.SdWebuiResponseUtils;

import java.io.IOException;

public class DefaultImage2ImageService implements Image2Image {

    private static final String IMG2IMG_PATH = "/sdapi/v1/img2img";

    private final SdWebuiBeanContainer container;

    public DefaultImage2ImageService(SdWebuiBeanContainer container) {
        this.container = container;
    }

    @Override
    public Image2ImageResult img2img(Image2ImageOptions options) {
        HttpClient httpClient = container.getBean(HttpClient.class);
        try {
            return httpClient.execute(buildRequest(options), this::parseResponse);
        } catch (IOException e) {
            throw new SdBusinessException(e);
        }
    }

    ClassicHttpRequest buildRequest(Image2ImageOptions options) {
        String url = container.getBean(SdWebuiOptions.class).getEndpoint() + IMG2IMG_PATH;
        ClassicHttpRequest request = new HttpPost(url);
        request.setEntity(new StringEntity(JsonUtils.toJson(options)));
        request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        return request;
    }

    Image2ImageResult parseResponse(ClassicHttpResponse response) {
        SdWebuiResponseUtils.checkResponseStatus(response);
        try {
            return JsonUtils.fromJson(response.getEntity().getContent(), Image2ImageResult.class);
        } catch (IOException e) {
            throw new SdBusinessException(e);
        }
    }

}
