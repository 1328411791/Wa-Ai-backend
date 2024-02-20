package org.talang.sdk.services;

import org.talang.sdk.GetFaceRestorers;
import org.talang.sdk.SdWebuiBeanContainer;
import org.talang.sdk.models.results.FaceRestorer;

import java.util.Arrays;
import java.util.List;

public class DefaultGetFaceRestorersService implements GetFaceRestorers {

    private final SdWebuiBeanContainer container;

    public DefaultGetFaceRestorersService(SdWebuiBeanContainer container) {
        this.container = container;
    }

    @Override
    public List<FaceRestorer> getFaceRestorers() {
        return Arrays.asList(container.getBean(CommonGetService.class).getData("/sdapi/v1/face-restorers", FaceRestorer[].class));
    }

}
