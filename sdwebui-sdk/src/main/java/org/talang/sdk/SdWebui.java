package org.talang.sdk;


import org.talang.sdk.models.SdWebuiOptions;
import org.talang.sdk.services.SdWebuiInvocationHandler;

import java.lang.reflect.Proxy;

public interface SdWebui extends SystemInfoFetcher, Txt2Image, Image2Image, ExtraImage, GetSdModels, GetFaceRestorers {

    static SdWebui create(String endpoint) {
        SdWebuiOptions options = new SdWebuiOptions(endpoint);
        return (SdWebui) Proxy.newProxyInstance(SdWebui.class.getClassLoader(), new Class[]{SdWebui.class}, new SdWebuiInvocationHandler(options));
    }

}
