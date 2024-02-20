package org.talang.sdk.services;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.talang.sdk.SdWebuiBeanContainer;
import org.talang.sdk.models.SdWebuiOptions;
import org.talang.sdk.utils.SdWebuiResponseUtils;

import java.io.IOException;

final class CommonGetService {

    private final SdWebuiBeanContainer container;

    public CommonGetService(SdWebuiBeanContainer container) {
        this.container = container;
    }

    public <T> T getData(String path, Class<T> clazz) {
        HttpClient client = this.container.getBean(HttpClient.class);
        SdWebuiOptions sdWebuiOptions = this.container.getBean(SdWebuiOptions.class);
        try {
            return client.execute(new HttpGet(sdWebuiOptions.getEndpoint() + path),
                    response -> SdWebuiResponseUtils.parseResponse(response, clazz));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
