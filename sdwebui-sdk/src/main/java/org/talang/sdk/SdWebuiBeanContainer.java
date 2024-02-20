package org.talang.sdk;

import org.talang.sdk.models.SdWebuiOptions;
import org.talang.sdk.services.DefaultSdWebuiBeanContainer;

public interface SdWebuiBeanContainer {

    static SdWebuiBeanContainer create(SdWebuiOptions options) {
        return new DefaultSdWebuiBeanContainer(options);
    }

    <T> T getBean(Class<T> serviceClass);

    void register(Class<?> serviceClass, Object service);

}
